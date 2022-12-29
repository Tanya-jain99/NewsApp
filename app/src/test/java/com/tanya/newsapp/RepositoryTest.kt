package com.tanya.newsapp

import app.cash.turbine.test
import com.tanya.newsapp.data.api.NetworkService
import com.tanya.newsapp.data.local.DataBaseHelper
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.data.model.NetworkArticle
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    @Mock
    private lateinit var dbHelper: DataBaseHelper

    @Test
    fun givenServerResponse200_whenFetch_returnsSuccess(){
        runTest {
            doReturn(emptyList<NetworkArticle>()).`when`(networkService)
                .getTopHeadlines(anyMap())
            doNothing().`when`(dbHelper).clearArticles()
            doNothing().`when`(dbHelper).insertArticle(emptyList())
            doReturn(flowOf(emptyList<NetworkArticle>())).`when`(dbHelper).
            getArticles(anyString(), anyString())
            val repository = TopHeadlineRepository(networkService, dbHelper)
            val articles = repository.getTopHeadlines(anyMap())
            articles.test {
                Assert.assertEquals(emptyList<Article>(), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkService, times(1)).
            getTopHeadlines(anyMap())
        }
    }

    @Test
    fun givenOfflineMode_whenFetch_returnsSuccess(){
        runTest {
            doReturn(flowOf(emptyList<NetworkArticle>())).`when`(dbHelper)
                .getArticles(anyString(), anyString())
            val repository = TopHeadlineRepository(networkService, dbHelper)
            val articles = repository.getLocalTopHeadLines(mapOf(Pair("country", "us")))
            articles.test{
                Assert.assertEquals(emptyList<Article>(), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(dbHelper, times(1)).
            getArticles(anyString(), anyString())
        }
    }





}