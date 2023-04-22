package com.tanya.newsapp.di.component

import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.di.module.LanguageModule
import com.tanya.newsapp.ui.view.LanguageFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class] , modules = [LanguageModule::class])
interface LanguageComponent {

    fun injectDependencies(fragment : LanguageFragment)
}