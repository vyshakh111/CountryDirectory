package di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ContextModule {
    @Singleton
    @Provides
    @Named("AppContext")
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}