package com.tanya.newsapp.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.FragmentContext
import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.ui.base.ViewModelProviderFactory
import com.tanya.newsapp.ui.view.NewsListFragment
import com.tanya.newsapp.ui.adapter.TopHeadlineAdapter
import com.tanya.newsapp.ui.viewmodel.TopHeadlineViewModel
import dagger.Module
import dagger.Provides

@Module
class NewsListModule(private val fragment: NewsListFragment) {

    @FragmentContext
    @Provides
    fun provideContext() : Context{
        return fragment.requireContext()
    }

    @Provides
    fun provideNewsListViewModel(topHeadlineRepository: TopHeadlineRepository): TopHeadlineViewModel {
        return ViewModelProvider(fragment,
            ViewModelProviderFactory(TopHeadlineViewModel::class) {
                TopHeadlineViewModel(topHeadlineRepository)
            })[TopHeadlineViewModel::class.java]
    }

    @FragmentScope
    @Provides
    fun provideDummiesAdapter() = TopHeadlineAdapter(ArrayList())

}