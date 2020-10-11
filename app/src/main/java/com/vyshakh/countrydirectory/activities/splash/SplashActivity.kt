package com.vyshakh.countrydirectory.activities.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vyshakh.countrydirectory.R
import com.vyshakh.countrydirectory.activities.base.BaseActivity
import com.vyshakh.countrydirectory.activities.main.MainActivity
import di.DaggerViewModelFactory
import javax.inject.Inject

class SplashActivity : BaseActivity() {
    private lateinit var splashViewModel: SplashViewModel
    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel =
            ViewModelProvider(this, daggerViewModelFactory).get(SplashViewModel::class.java)
        splashViewModel.liveData.observe(this, Observer {
            when (it) {
                is SplashState.MainActivity -> {
                    finish()
                    gotoMainActivity()
                }
            }
        })
    }

    private fun gotoMainActivity() = startActivity(Intent(this, MainActivity::class.java))
}