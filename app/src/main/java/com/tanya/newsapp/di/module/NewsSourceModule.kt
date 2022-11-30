package com.tanya.newsapp.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.NewsSourceFragmentContext
import com.tanya.newsapp.ui.base.ViewModelProviderFactory
import com.tanya.newsapp.ui.viewmodel.CategoryViewModel
import com.tanya.newsapp.ui.view.NewsSourceFragment
import com.tanya.newsapp.ui.adapter.NewsCategoryAdapter
import dagger.Module
import dagger.Provides

@Module
class NewsSourceModule(private val fragment: NewsSourceFragment) {

    @NewsSourceFragmentContext
    @Provides
    fun providesContext(): Context {
        return fragment.requireContext()
    }

    @Provides
    fun provideCategoryListViewModel(topHeadlineRepository: TopHeadlineRepository): CategoryViewModel {
        return ViewModelProvider(fragment,
            ViewModelProviderFactory(CategoryViewModel::class) {
                CategoryViewModel(topHeadlineRepository)
            })[CategoryViewModel::class.java]
    }

    @Provides
    fun providesCategoryAdapter(): NewsCategoryAdapter {
        return NewsCategoryAdapter(ArrayList())
    }

}