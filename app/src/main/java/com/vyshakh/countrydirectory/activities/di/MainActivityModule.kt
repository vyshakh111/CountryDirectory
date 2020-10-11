package com.vyshakh.countrydirectory.activities.di

import com.vyshakh.countrydirectory.helper.AddItemDialogFragment
import androidx.lifecycle.ViewModel
import com.vyshakh.countrydirectory.activities.main.CountryDetailsFragment
import com.vyshakh.countrydirectory.activities.main.CountryListFragment
import com.vyshakh.countrydirectory.activities.main.viewmodel.MainViewModelImpl
import com.vyshakh.countrydirectory.activities.main.data.IMainRepository
import com.vyshakh.countrydirectory.activities.main.data.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import di.ViewModelKey

@Module
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModelImpl::class)
    abstract fun bindMainActivityModel(mainViewModelImpl: MainViewModelImpl):ViewModel

    @ContributesAndroidInjector
    abstract fun bindCountryListFragment(): CountryListFragment

    @ContributesAndroidInjector
    abstract fun bindCountryDetailsFragment(): CountryDetailsFragment

    @ContributesAndroidInjector
    abstract fun bindAppDialogFragment(): AddItemDialogFragment

    @Binds
    abstract fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): IMainRepository
}