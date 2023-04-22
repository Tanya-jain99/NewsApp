package com.tanya.newsapp.di.component

import android.content.Context
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.data.local.AppDatabase
import com.tanya.newsapp.data.api.NetworkService
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.ApplicationContext
import com.tanya.newsapp.di.module.ApplicationModule
import com.tanya.newsapp.ui.DispatcherProviderImpl
import com.tanya.newsapp.utils.Logger
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getAppDatabase() : AppDatabase

    fun getDispatcherProvider() : DispatcherProviderImpl

    fun getLogger() : Logger

}