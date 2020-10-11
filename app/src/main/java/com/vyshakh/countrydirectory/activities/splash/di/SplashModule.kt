package com.vyshakh.countrydirectory.activities.splash.di

import androidx.lifecycle.ViewModel
import com.vyshakh.countrydirectory.activities.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import di.ViewModelKey

@Module
abstract class SplashModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}