package com.tanya.newsapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.FragmentContext
import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.ui.DispatcherProviderImpl
import com.tanya.newsapp.ui.adapter.LanguageAdapter
import com.tanya.newsapp.ui.base.ViewModelProviderFactory
import com.tanya.newsapp.ui.view.LanguageFragment
import com.tanya.newsapp.ui.viewmodel.LanguageViewModel
import dagger.Module
import dagger.Provides


@Module
class LanguageModule(private val fragment: LanguageFragment) {

    @FragmentContext
    @Provides
    fun providesContext()  = fragment.requireContext()

    @Provides
    fun providesLanguageViewModel(repository: TopHeadlineRepository,
                                  dispatcherProviderImpl: DispatcherProviderImpl) : LanguageViewModel {
        return ViewModelProvider(fragment, ViewModelProviderFactory(LanguageViewModel::class){
               LanguageViewModel(repository, dispatcherProviderImpl)
        })[LanguageViewModel::class.java]
    }

    @FragmentScope
    @Provides
    fun providesLanguageAdapter() : LanguageAdapter{
        return LanguageAdapter(ArrayList())
    }

}