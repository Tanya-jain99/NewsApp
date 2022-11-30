package com.tanya.newsapp.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class FragmentScope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class NewsBaseFragmentScope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class CountryFragmentScope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class LanguageFragmentScope