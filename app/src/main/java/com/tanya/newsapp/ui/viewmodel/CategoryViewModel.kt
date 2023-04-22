package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.api.models.Source
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.ui.base.BaseViewModel
import com.tanya.newsapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class CategoryViewModel(private val topHeadlineRepository: TopHeadlineRepository,
                        private val dispatcherProvider: DispatcherProvider
): BaseViewModel() {

    private var _srcList = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)
    val srcList: StateFlow<UiState<List<Source>>> = _srcList


    init {
        fetchSources()
    }


    private fun fetchSources() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getNewsSources()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _srcList.value = UiState.Error(e.toString())
                }
                .collect {
                    _srcList.value = UiState.Success(it)
                }
        }
    }

}