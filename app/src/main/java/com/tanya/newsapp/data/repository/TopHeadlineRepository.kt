package com.tanya.newsapp.data.repository

import com.tanya.newsapp.data.api.NetworkService
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.data.model.Language
import com.tanya.newsapp.data.model.Country
import com.tanya.newsapp.data.model.Source
import com.tanya.newsapp.utils.DataUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(params: Map<String?,String?>): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(params))
        }.map {
            it.articles
        }
    }

    fun getCountries(): Flow<List<Country>> {
        return flow {
            emit(DataUtils.getCountries())
        }
    }

    fun getLanguages(): Flow<List<Language>> {
        return flow {
            emit(DataUtils.getLanguages())
        }
    }

    fun getNewsSources() : Flow<List<Source>>{
        return flow {
            emit(networkService.getNewsSources())
        }.map{
            it.sources
        }
    }
}