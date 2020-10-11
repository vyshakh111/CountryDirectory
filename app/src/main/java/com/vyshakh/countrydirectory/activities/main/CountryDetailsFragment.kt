package com.vyshakh.countrydirectory.activities.main

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vyshakh.countrydirectory.R
import com.vyshakh.countrydirectory.activities.base.BaseFragment
import com.vyshakh.countrydirectory.activities.main.data.CountryListItem
import com.vyshakh.countrydirectory.activities.main.viewmodel.IMainViewModel
import com.vyshakh.countrydirectory.activities.main.viewmodel.MainViewModelImpl
import com.vyshakh.countrydirectory.helper.AddItemDialogFragment
import di.DaggerViewModelFactory
import kotlinx.android.synthetic.main.add_item_layout.view.*
import kotlinx.android.synthetic.main.fragment_country_details.*
import javax.inject.Inject

class CountryDetailsFragment : BaseFragment() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val mainViewModel: IMainViewModel? by lazy {
        activity?.run {
            ViewModelProvider(this, daggerViewModelFactory).get(MainViewModelImpl::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    isEnabled = false
                    activity?.finish()
                }
            }
        }
        if (parentFragmentManager.backStackEntryCount == 1)
            requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.run {
            (this as AppCompatActivity).supportActionBar?.title = getString(R.string.country_details_label)
        }

        fab_menu_show.setOnClickListener {
            if (ll_add_code.visibility == View.GONE) {
                shoFabAddItem()
            } else {
                hideFabAddItem()
            }
        }

        fab_add_item.setOnClickListener {
            AddItemDialogFragment.newInstance().show(parentFragmentManager, AddItemDialogFragment.TAG)
            hideFabAddItem()
        }
        view_blur.setOnClickListener { hideFabAddItem() }

        mainViewModel?.selectedItem?.observe(viewLifecycleOwner, Observer {
            displayCountryDetails(it)
        })
    }

    private fun displayCountryDetails(selectedCountry: CountryListItem?) {
        tv_country_name_header.text = selectedCountry?.countryName
        tv_country_name.text = selectedCountry?.id
        tv_desc.text = selectedCountry?.description
        tv_url.text = selectedCountry?.url
        tv_country_code.visibility = View.GONE
        tv_country_code.text = ""
        selectedCountry?.countryCode?.let {
            tv_country_code.visibility = View.VISIBLE
            tv_country_code.text = String.format("%s : %S",getString(R.string.country_code_label),selectedCountry.countryCode)
        }
    }

    private fun hideFabAddItem() {
        changeBlurViewVisibility(View.GONE)
        hideAnimation(ll_add_code)
    }

    private fun shoFabAddItem() {
        changeBlurViewVisibility(View.VISIBLE)
        showAnimation(ll_add_code)
    }

    private fun showAnimation(view: View) {
        view.animate()
            .translationY(-50f)
            .alpha(1.0f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {
                    view.visibility = View.VISIBLE
                    view.alpha = 0.0f
                }
            })
            .start()
    }

    private fun hideAnimation(view: View) {
        view.animate()
            .translationY(0f)
            .alpha(0.0f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    view.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {
                }
            })
            .start()
    }

    private fun changeBlurViewVisibility(visibility: Int) {
        view_blur.visibility = visibility
    }
}