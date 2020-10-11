package com.vyshakh.countrydirectory.activities.main

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vyshakh.countrydirectory.R
import com.vyshakh.countrydirectory.activities.base.BaseFragment
import com.vyshakh.countrydirectory.activities.main.data.CountryFragmentStateValue
import com.vyshakh.countrydirectory.activities.main.data.CountryListAdapter
import com.vyshakh.countrydirectory.activities.main.data.CountryListItem
import com.vyshakh.countrydirectory.activities.main.viewmodel.IMainViewModel
import com.vyshakh.countrydirectory.activities.main.viewmodel.MainViewModelImpl
import di.DaggerViewModelFactory
import kotlinx.android.synthetic.main.fragment_countrylist.*
import javax.inject.Inject

class CountryListFragment : BaseFragment() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val mainViewModel: IMainViewModel? by lazy {
        activity?.run {
            ViewModelProvider(this, daggerViewModelFactory).get(MainViewModelImpl::class.java)
        }
    }
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var navigationListener: NavigationListener? = null

    private val viewAdapter: CountryListAdapter? by lazy {
        CountryListAdapter(
            itemClickListener = object : CountryListAdapter.OnItemClickListener {
                override fun onItemClick(item: CountryListItem?,position: Int) {
                    mainViewModel?.setSelectedItemIndex(position)
                    navigationListener?.navigateToFragment(CountryFragmentStateValue.CountryDetailsFragment)
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    isEnabled = false
                    activity?.finish()
                }
            }
        }
            requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countrylist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.run {
            (this as AppCompatActivity).supportActionBar?.title = getString(R.string.country_list_label)
        }
        viewManager = LinearLayoutManager(requireContext())
        rv_country_list.layoutManager = viewManager
        rv_country_list.adapter = viewAdapter
        swipe_refresh.setOnRefreshListener {
            getCountryList()
        }
        getCountryList()
    }

    private fun getCountryList(){
        mainViewModel?.getCountryList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigationListener = activity?.run { this as NavigationListener }
            ?: throw Exception("Invalid Activity Please implement Navigation listener.")
        setObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.search_item)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setBackgroundColor(Color.WHITE)
        searchView.setIconifiedByDefault(true)
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewAdapter?.filter?.filter(newText)
                return false
            }
        })
    }

    private fun setObservers() {
        mainViewModel?.countryListResponse?.observe(viewLifecycleOwner, Observer {
            val countryListResponse = it ?: return@Observer
            if (countryListResponse.isNotEmpty())
                displayData(it)
            else
                showEmptyView()
        })
    }

    private fun displayData(countryList: List<CountryListItem>?) {
        rv_country_list.visibility = View.VISIBLE
        tv_empty_message.visibility = View.GONE
        viewAdapter?.setData(countryList)
        swipe_refresh.isRefreshing = false
    }

    private fun showEmptyView() {
        rv_country_list.visibility = View.GONE
        tv_empty_message.visibility = View.VISIBLE
    }
}