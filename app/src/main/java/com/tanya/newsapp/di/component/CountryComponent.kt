package com.tanya.newsapp.di.component

import com.tanya.newsapp.di.CountryFragmentScope
import com.tanya.newsapp.di.module.CountryModule
import com.tanya.newsapp.ui.view.CountryFragment
import dagger.Component

@CountryFragmentScope
@Component(modules = [CountryModule::class], dependencies = [ApplicationComponent::class])
interface CountryComponent {

    fun injectDependencies(fragment: CountryFragment)

}