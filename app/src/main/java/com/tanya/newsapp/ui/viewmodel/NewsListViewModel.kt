package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.ui.base.BaseViewModel
import com.tanya.newsapp.utils.Logger
import com.tanya.newsapp.utils.UiState
import com.tanya.newsapp.utils.asModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class NewsListViewModel(private val topHeadlineRepository: TopHeadlineRepository,
                        private val dispatcherProvider: DispatcherProvider,
                        private val logger:Logger) : BaseViewModel() {

    private val _articleList = MutableStateFlow<UiState<PagingData<Article>>>(UiState.Loading)
    val articleList : StateFlow<UiState<PagingData<Article>>> = _articleList
    private val tag : String = NewsListViewModel::class.java.name

    fun fetchNews(data: LinkedHashMap<String,String>)  {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getArticles(data)
                .map { pagingData->
                    pagingData.map { it.asModel() }
                }
                .cachedIn(viewModelScope)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    logger.e(tag,"Error while retrieving the data from internet")
                    _articleList.value = UiState.Error(e.toString())
                }
                .collect {
                    logger.d(tag, it.toString())
                    _articleList.value = UiState.Success(it)
                }
        }
    }
}