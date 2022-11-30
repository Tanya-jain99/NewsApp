package com.tanya.newsapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.LanguageFragmentContext
import com.tanya.newsapp.ui.adapter.LanguageAdapter
import com.tanya.newsapp.ui.base.ViewModelProviderFactory
import com.tanya.newsapp.ui.view.LanguageFragment
import com.tanya.newsapp.ui.viewmodel.LanguageViewModel
import dagger.Module
import dagger.Provides


@Module
class LanguageModule(private val fragment: LanguageFragment) {

    @LanguageFragmentContext
    @Provides
    fun providesContext()  = fragment.requireContext()

    @Provides
    fun providesLanguageViewModel(repository: TopHeadlineRepository) : LanguageViewModel {
        return ViewModelProvider(fragment, ViewModelProviderFactory(LanguageViewModel::class){
               LanguageViewModel(repository)
        })[LanguageViewModel::class.java]
    }

    @Provides
    fun providesLanguageAdapter() : LanguageAdapter{
        return LanguageAdapter(ArrayList())
    }

}