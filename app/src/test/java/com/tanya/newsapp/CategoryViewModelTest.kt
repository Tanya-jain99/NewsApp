package com.tanya.newsapp

import app.cash.turbine.test
import com.tanya.newsapp.data.model.Source
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.ui.viewmodel.CategoryViewModel
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
class CategoryViewModelTest {

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
            doReturn(flowOf(emptyList<Source>()))
                .`when`(repository).getNewsSources()
            val viewModel = CategoryViewModel(repository, dispatcherProvider)
            viewModel.srcList.test {
                Assert.assertEquals(Resource.success(emptyList<List<Source>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(repository, times(1)).getNewsSources()
        }
    }

    @Test
    fun givenServerResponse500_whenFetch_shouldReturnFailure() {
        runTest {
            val errorMessage = "an error occurred"
            doReturn(flow<List<Source>> {
                throw IllegalStateException(errorMessage)
            }).`when`(repository).getNewsSources()
            val viewModel = CategoryViewModel(repository, dispatcherProvider)
            viewModel.srcList.test {
                Assert.assertEquals(
                    Resource.error<List<Source>>(IllegalStateException(errorMessage)
                        .toString()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(repository, times(1)).getNewsSources()
        }
    }
}