package com.tanya.newsapp.di.component

import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.di.module.NewsSourceModule
import com.tanya.newsapp.ui.view.NewsSourceFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [NewsSourceModule::class])
interface NewsBaseComponent {

    fun injectDependencies(fragment: NewsSourceFragment)
}