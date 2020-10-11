package di


import com.vyshakh.countrydirectory.activities.di.MainActivityModule
import com.vyshakh.countrydirectory.activities.main.MainActivity
import com.vyshakh.countrydirectory.activities.splash.SplashActivity
import com.vyshakh.countrydirectory.activities.splash.SplashState
import com.vyshakh.countrydirectory.activities.splash.di.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity

}
