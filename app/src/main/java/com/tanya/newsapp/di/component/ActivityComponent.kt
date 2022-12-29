package com.tanya.newsapp.di.component

import com.tanya.newsapp.di.ActivityScope
import com.tanya.newsapp.di.module.ActivityModule
import com.tanya.newsapp.ui.base.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

}