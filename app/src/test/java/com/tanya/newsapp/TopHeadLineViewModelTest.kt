package com.tanya.newsapp

import app.cash.turbine.test
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.ui.viewmodel.TopHeadlineViewModel
import com.tanya.newsapp.utils.TestDispatcherProvider
import com.tanya.newsapp.utils.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadLineViewModelTest {

    @Mock
    private lateinit var repository: TopHeadlineRepository

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var logger: Logger

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        logger = TestLogger()
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        runTest {
            doReturn(flowOf(emptyList<Article>())).`when`(repository).getTopHeadlines(anyMap())
            val viewModel = TopHeadlineViewModel(repository, dispatcherProvider, logger)
            viewModel.fetchNews(anyMap())
            viewModel.articleList.test {
                assertEquals(Resource.success(emptyList<List<Article>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(repository).getTopHeadlines(anyMap())
        }
    }

    @Test
    fun givenServerResponse500_whenFetch_shouldReturnFailure() {
        runTest {
            val errorMessage = "an error occurred"
            doReturn(flow<List<Article>> {
                throw IllegalStateException(errorMessage)
            }).`when`(repository).getTopHeadlines(anyMap())
            val viewModel = TopHeadlineViewModel(repository, dispatcherProvider, logger)
            viewModel.fetchNews(anyMap())
            viewModel.articleList.test {
                assertEquals(Resource.error<List<Article>>(IllegalStateException(errorMessage)
                    .toString()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(repository).getTopHeadlines(anyMap())
        }
    }

    @Test
    fun givenDataPresentInDb_whenFetch_shouldReturnSuccess() {
        runTest {
            doReturn(flowOf<List<Article>>(emptyList())).`when`(repository)
                .getLocalTopHeadLines(anyMap())
            val viewModel = TopHeadlineViewModel(repository, dispatcherProvider, logger)
            viewModel.fetchNewsFromDB(anyMap())
            viewModel.articleList.test {
                 assertEquals(Resource.success(emptyList<List<Article>>()), awaitItem())
                 cancelAndIgnoreRemainingEvents()
            }
            verify(repository, times(1)).getLocalTopHeadLines(anyMap())
        }
    }

    @Test
    fun givenDataAbsentInDb_whenFetch_shouldReturnFail(){
        runTest {
            val errorMessage = "error occurred"
            doReturn(flow<List<Article>> {
                throw IllegalStateException(errorMessage)
            }).`when`(repository)
                .getLocalTopHeadLines(anyMap())
            val viewModel = TopHeadlineViewModel(repository, dispatcherProvider, logger)
            viewModel.fetchNewsFromDB(anyMap())
            viewModel.articleList.test {
                assertEquals(Resource.error<List<Article>>(IllegalStateException(errorMessage)
                    .toString()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(repository, times(1)).getLocalTopHeadLines(anyMap())
        }
    }


    }