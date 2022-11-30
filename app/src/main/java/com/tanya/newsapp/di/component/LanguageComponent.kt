package com.tanya.newsapp.di.component

import com.tanya.newsapp.di.LanguageFragmentScope
import com.tanya.newsapp.di.module.LanguageModule
import com.tanya.newsapp.ui.view.LanguageFragment
import dagger.Component

@LanguageFragmentScope
@Component(dependencies = [ApplicationComponent::class] , modules = [LanguageModule::class])
interface LanguageComponent {

    fun injectDependencies(fragment : LanguageFragment)
}