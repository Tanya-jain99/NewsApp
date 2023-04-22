package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.api.models.NetworkArticle
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.utils.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FragmentScope
class SearchViewModel(private val repository: TopHeadlineRepository,
                      private val dispatcherProvider: DispatcherProvider): ViewModel() {

    private val _searchList = MutableStateFlow<UiState<List<NetworkArticle>>>(UiState.Loading)
    val searchList : StateFlow<UiState<List<NetworkArticle>>> = _searchList

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun getSearchResult(queryFlow: StateFlow<String>)  {
        viewModelScope.launch(dispatcherProvider.main) {

            queryFlow.debounce(300)
                .distinctUntilChanged()
                .filter { query ->
                    if (query.isEmpty() || query.length < 3) {
                        _searchList.value = UiState.Success(emptyList())
                        return@filter false
                    }else {
                        return@filter true
                    }
                }
                .flatMapLatest { query ->
                    return@flatMapLatest repository.getSearchResults(query).catch {
                        emitAll(flowOf(emptyList()))
                    }
                }.flowOn(dispatcherProvider.io)
                .collect {
                      _searchList.value = UiState.Success(it)
                }

        }

    }


}