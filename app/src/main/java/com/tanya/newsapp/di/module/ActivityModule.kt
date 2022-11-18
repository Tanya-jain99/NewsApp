package com.tanya.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.ActivityContext
import com.tanya.newsapp.ui.base.ViewModelProviderFactory
import com.tanya.newsapp.ui.topheadline.TopHeadlineAdapter
import com.tanya.newsapp.ui.topheadline.TopHeadlineViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }
}