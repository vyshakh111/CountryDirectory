package com.vyshakh.countrydirectory.activities.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.vyshakh.countrydirectory.R
import com.vyshakh.countrydirectory.activities.base.BaseActivity
import com.vyshakh.countrydirectory.activities.main.data.CountryFragmentStateValue

class MainActivity : BaseActivity(), NavigationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initToolBar(getString(R.string.country_list_label))
        if (savedInstanceState == null)
            navigateToFragment(CountryFragmentStateValue.CountryListFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(fragment.tag)
        fragmentTransaction.commit()
    }

    override fun navigateToFragment(fragmentType: CountryFragmentStateValue) {
        when (fragmentType) {
            CountryFragmentStateValue.CountryListFragment -> loadFragment(CountryListFragment())
            CountryFragmentStateValue.CountryDetailsFragment -> loadFragment(CountryDetailsFragment())
        }
    }
}
interface NavigationListener {
    fun navigateToFragment(fragmentType: CountryFragmentStateValue)
}