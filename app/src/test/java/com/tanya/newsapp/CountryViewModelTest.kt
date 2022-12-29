package com.tanya.newsapp

import app.cash.turbine.test
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.data.model.Country
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.ui.viewmodel.CountryViewModel
import com.tanya.newsapp.utils.Resource
import com.tanya.newsapp.utils.TestDispatcherProvider
import junit.framework.Assert
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
class CountryViewModelTest {

    @Mock
    private lateinit var repository: TopHeadlineRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        runTest {
            doReturn(flowOf(emptyList<Country>()))
                .`when`(repository).getCountries()
            val viewModel = CountryViewModel(repository, dispatcherProvider)
            viewModel.countrySrcList.test {
                Assert.assertEquals(Resource.success(emptyList<List<Country>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(repository, times(1)).getCountries()
        }
    }

    @Test
    fun givenServerResponse500_whenFetch_shouldReturnFailure() {
        runTest {
            val errorMessage = "an error occurred"
            doReturn(flow<List<Country>> {
                throw IllegalStateException(errorMessage)
            }).`when`(repository).getCountries()
            val viewModel = CountryViewModel(repository, dispatcherProvider)
            viewModel.countrySrcList.test {
                Assert.assertEquals(
                    Resource.error<List<Article>>(IllegalStateException(errorMessage)
                    .toString()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(repository, times(1)).getCountries()
        }
    }

}