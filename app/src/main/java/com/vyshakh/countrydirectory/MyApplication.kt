package com.vyshakh.countrydirectory

import android.content.Intent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import di.DaggerAppComponent


class MyApplication : DaggerApplication() {

    init {
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        var instance: MyApplication? = null
    }
}