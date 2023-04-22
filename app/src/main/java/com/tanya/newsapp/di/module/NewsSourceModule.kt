package com.tanya.newsapp.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.FragmentContext
import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.ui.DispatcherProviderImpl
import com.tanya.newsapp.ui.adapter.NewsSourceAdapter
import com.tanya.newsapp.ui.base.ViewModelProviderFactory
import com.tanya.newsapp.ui.view.NewsSourceFragment
import com.tanya.newsapp.ui.viewmodel.CategoryViewModel
import dagger.Module
import dagger.Provides

@Module
class NewsSourceModule(private val fragment: NewsSourceFragment) {

    @FragmentContext
    @Provides
    fun providesContext(): Context {
        return fragment.requireContext()
    }

    @Provides
    fun provideCategoryListViewModel(topHeadlineRepository: TopHeadlineRepository,
                                     dispatcherProviderImpl: DispatcherProviderImpl): CategoryViewModel {
        return ViewModelProvider(fragment,
            ViewModelProviderFactory(CategoryViewModel::class) {
                CategoryViewModel(topHeadlineRepository, dispatcherProviderImpl)
            })[CategoryViewModel::class.java]
    }

    @FragmentScope
    @Provides
    fun providesCategoryAdapter(): NewsSourceAdapter {
        return NewsSourceAdapter(ArrayList())
    }

}