package com.tanya.newsapp.di.module

import android.content.Context
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.data.api.NetworkService
import com.tanya.newsapp.data.api.Networking
import com.tanya.newsapp.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService = Networking.create()

}