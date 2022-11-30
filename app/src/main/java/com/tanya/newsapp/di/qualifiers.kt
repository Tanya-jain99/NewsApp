package com.tanya.newsapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class NewsListFragmentContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class NewsSourceFragmentContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class CountryFragmentContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class LanguageFragmentContext