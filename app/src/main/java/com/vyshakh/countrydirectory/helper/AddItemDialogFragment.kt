package com.vyshakh.countrydirectory.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vyshakh.countrydirectory.R
import com.vyshakh.countrydirectory.activities.main.viewmodel.IMainViewModel
import com.vyshakh.countrydirectory.activities.main.viewmodel.MainViewModelImpl
import dagger.android.support.DaggerDialogFragment
import di.DaggerViewModelFactory
import kotlinx.android.synthetic.main.add_item_layout.view.*
import javax.inject.Inject

class AddItemDialogFragment : DaggerDialogFragment(){

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val mainViewModel: IMainViewModel? by lazy {
        activity?.run {
            ViewModelProvider(this, daggerViewModelFactory).get(MainViewModelImpl::class.java)
        }
    }

    companion object {

        const val TAG = "AddItemDialogFragment"

        fun newInstance(): AddItemDialogFragment {
            return AddItemDialogFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }



    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        view.edt_in_country_code.doAfterTextChanged {
            mainViewModel?.countryCodeTextChanged(it.toString())
        }

        mainViewModel?.inputFormState?.observe(viewLifecycleOwner, Observer {
            val iniFormState = it ?: return@Observer
            when {
                iniFormState.textError != null -> {
                    view.edt_in_country_code?.error = getString(iniFormState.textError)
                }
                else -> {
                    view.edt_in_country_code?.error = null
                }
            }
        })

    }

    private fun setupClickListeners(view: View) {
        view.btn_save.setOnClickListener {
            val item = mainViewModel?.selectedItem?.value
            item?.countryCode = view.edt_in_country_code.text.toString()
            mainViewModel?.updateItemAtSelectedIndex(item)
            dismiss()
        }

        view.btn_cancel.setOnClickListener {
            dismiss()
        }
    }

}