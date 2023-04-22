package com.tanya.newsapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tanya.newsapp.data.api.NetworkService
import com.tanya.newsapp.data.local.DataBaseHelper
import com.tanya.newsapp.data.paging.NewsRemoteMediator
import com.tanya.newsapp.data.model.Country
import com.tanya.newsapp.data.model.Language
import com.tanya.newsapp.data.api.models.NetworkArticle
import com.tanya.newsapp.data.api.models.Source
import com.tanya.newsapp.utils.DataUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService,
                                                private val dbHelper : DataBaseHelper) {
    @OptIn(ExperimentalPagingApi::class)
    fun getArticles(data: LinkedHashMap<String, String>) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 30,
            prefetchDistance = 10,
            initialLoadSize = 10
        ),
        remoteMediator = NewsRemoteMediator(data, networkService, dbHelper),
        pagingSourceFactory = { dbHelper.getArticles(data.keys.first(), data.values.first()) }
    ).flow


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

    fun getNewsSources() : Flow<List<Source>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map{
            it.sources
        }
    }

    fun getSearchResults(query : String) : Flow<List<NetworkArticle>> {
        return flow {
            emit(networkService.search(query))
        }.map {
            it.articles
        }
    }
}