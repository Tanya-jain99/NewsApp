package com.tanya.newsapp.data.repository

import com.tanya.newsapp.data.api.NetworkService
import com.tanya.newsapp.data.local.DataBaseHelper
import com.tanya.newsapp.data.model.*
import com.tanya.newsapp.utils.DataUtils
import com.tanya.newsapp.utils.asEntity
import com.tanya.newsapp.utils.asModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService,
                                                private val dbHelper : DataBaseHelper) {

    fun getTopHeadlines(params: Map<String?,String?>): Flow<List<Article>> {

        val category = params.keys.first().toString()
        val categoryId = params.values.first().toString()

        return flow {
            emit(networkService.getTopHeadlines(params).articles)
        }.map { articles -> articles.map {
            return@map it.asEntity(category, categoryId)
        }
        }.flatMapConcat { articles ->
            return@flatMapConcat flow {
                emit(dbHelper.clearArticles())
            }.flatMapConcat {
                return@flatMapConcat flow {
                   emit( dbHelper.insertArticle(articles))
                }
            }.flatMapConcat {
                return@flatMapConcat dbHelper.getArticles(category , categoryId)
                    .map { entities ->
                        entities.map {
                            it.asModel()
                        }
                    }
            }
        }
    }


    fun getLocalTopHeadLines(data: Map<String?, String?>): Flow<List<Article>> {

        val category: String = data.keys.first().toString()
        val categoryId : String = data.values.first().toString()

        return dbHelper.getArticles(category, categoryId).map {
            println("${Thread.currentThread()} has run.")
            it.map {
                article -> article.asModel()
            }
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