package com.vyshakh.countrydirectory.activities.base

import com.vyshakh.countrydirectory.R
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity() {
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun initToolBar(title:String){
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = title
    }
    fun setToolBarTitle(title:String){
        supportActionBar?.title = title
    }

}
