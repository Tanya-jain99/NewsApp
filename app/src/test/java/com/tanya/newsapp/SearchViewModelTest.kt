package com.tanya.newsapp

import app.cash.turbine.test
import com.tanya.newsapp.data.model.NetworkArticle
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.ui.viewmodel.SearchViewModel
import com.tanya.newsapp.utils.Resource
import com.tanya.newsapp.utils.TestDispatcherProvider
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest  {

    @Mock
    private lateinit var repository: TopHeadlineRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun givenEmptyString_whenFetch_shouldReturnSuccess() {
        runTest {
            doReturn(flowOf(emptyList<NetworkArticle>()))
                .`when`(repository).getSearchResults(" ")
            val viewModel = SearchViewModel(repository, dispatcherProvider)
            val flow = MutableStateFlow(" ")
            viewModel.getSearchResult(flow as StateFlow<String>)
            viewModel.searchList.test {
                Assert.assertEquals(Resource.success(emptyList<List<NetworkArticle>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(repository, times(1)).getSearchResults(" ")
        }
    }

    @Test
    fun givenServerResponse500_whenFetch_shouldReturnFailure() {
        runTest {
            val errorMessage = "an error occurred"
            doReturn(flow<List<NetworkArticle>> {
                throw IllegalStateException(errorMessage)
            }).`when`(repository).getSearchResults(anyString())
            val viewModel = SearchViewModel(repository, dispatcherProvider)
            viewModel.getSearchResult(MutableStateFlow("abcd"))
            viewModel.searchList.test {
                Assert.assertEquals(
                    Resource.error<List<NetworkArticle>>(IllegalStateException(errorMessage)
                        .toString()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(repository, times(1)).getSearchResults(anyString())
        }
    }

}