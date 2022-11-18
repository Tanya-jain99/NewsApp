package com.tanya.newsapp.di.component

import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.di.module.NewsListModule
import com.tanya.newsapp.ui.topheadline.NewsListFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [NewsListModule::class])
interface NewsListComponent {

    fun inject(fragment: NewsListFragment)
}