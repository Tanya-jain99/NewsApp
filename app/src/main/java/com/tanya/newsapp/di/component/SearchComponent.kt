package com.tanya.newsapp.di.component

import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.di.module.SearchModule
import com.tanya.newsapp.ui.view.SearchFragment
import dagger.Component

@FragmentScope
@Component(modules = [SearchModule::class] , dependencies = [ApplicationComponent::class])
interface SearchComponent {

    fun injectDependencies(fragment: SearchFragment)
}