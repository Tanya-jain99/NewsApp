package com.tanya.newsapp.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.FragmentContext
import com.tanya.newsapp.ui.DispatcherProviderImpl
import com.tanya.newsapp.ui.adapter.SearchAdapter
import com.tanya.newsapp.ui.base.ViewModelProviderFactory
import com.tanya.newsapp.ui.view.SearchFragment
import com.tanya.newsapp.ui.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides

@Module
class SearchModule(private val searchFragment: SearchFragment) {

    @FragmentContext
    @Provides
    fun providesContext(): Context {
        return searchFragment.requireContext()
    }

    @Provides
    fun providesAdapter() : SearchAdapter{
        return SearchAdapter(ArrayList())
    }

    @Provides
    fun providesViewModel(repository: TopHeadlineRepository, dispatcherProvider: DispatcherProviderImpl) : SearchViewModel{
        return ViewModelProvider(searchFragment,
            ViewModelProviderFactory(SearchViewModel::class){
            SearchViewModel(repository, dispatcherProvider)
        })[SearchViewModel::class.java]
    }

}