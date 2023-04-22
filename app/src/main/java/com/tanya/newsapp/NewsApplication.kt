package com.tanya.newsapp

import android.app.Application
import com.tanya.newsapp.di.component.ApplicationComponent
import com.tanya.newsapp.di.component.DaggerApplicationComponent
import com.tanya.newsapp.di.module.ApplicationModule

class NewsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}