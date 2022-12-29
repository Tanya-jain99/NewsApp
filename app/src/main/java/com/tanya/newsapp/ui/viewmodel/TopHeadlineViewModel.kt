package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.utils.Logger
import com.tanya.newsapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class TopHeadlineViewModel( private val topHeadlineRepository: TopHeadlineRepository,
                            private val dispatcherProvider: DispatcherProvider,
                            private val logger:Logger) : ViewModel() {

    private val _articleList = MutableStateFlow<Resource<List<Article>>>(Resource.loading())
    val articleList : StateFlow<Resource<List<Article>>> = _articleList
    private val tag : String = TopHeadlineViewModel::class.java.name

     fun fetchNews(data: Map<String?,String?>)  {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlines(data)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    logger.e(tag,"Error while retrieving the data from internet")
                    _articleList.value = Resource.error(e.toString())
                }
                .collect {
                    logger.d(tag, it.toString())
                    _articleList.value = Resource.success(it)
                }
        }
    }

    fun fetchNewsFromDB(data: Map<String?,String?>) {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getLocalTopHeadLines(data)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    logger.e(tag, e.toString())
                    _articleList.value = Resource.error(e.toString())
                }
                .collect {
                    _articleList.value = Resource.success(it)
                }
        }
    }

}