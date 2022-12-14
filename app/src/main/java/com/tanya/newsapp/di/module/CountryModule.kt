package com.tanya.newsapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.FragmentContext
import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.ui.adapter.CountryAdapter
import com.tanya.newsapp.ui.base.ViewModelProviderFactory
import com.tanya.newsapp.ui.view.CountryFragment
import com.tanya.newsapp.ui.viewmodel.CountryViewModel
import dagger.Module
import dagger.Provides

@Module
class CountryModule(private val fragment: CountryFragment) {

    @FragmentContext
    @Provides
    fun providesContext() = fragment.requireContext()


    @Provides
    fun providesCountryViewModel(repository: TopHeadlineRepository) : CountryViewModel {
           return ViewModelProvider(fragment,
               ViewModelProviderFactory(CountryViewModel::class) {
                   CountryViewModel(repository)
               })[CountryViewModel::class.java]
    }

    @FragmentScope
    @Provides
    fun providesCountryAdapter() : CountryAdapter{
        return CountryAdapter(ArrayList())
    }
}