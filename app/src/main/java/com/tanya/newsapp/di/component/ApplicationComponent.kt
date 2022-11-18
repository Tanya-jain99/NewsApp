package com.tanya.newsapp.di.component

import android.content.Context
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.data.api.NetworkService
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.ApplicationContext
import com.tanya.newsapp.di.module.ApplicationModule
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

}