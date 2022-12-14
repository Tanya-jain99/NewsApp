package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.model.NetworkArticle
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FragmentScope
class SearchViewModel(private val repository: TopHeadlineRepository): ViewModel() {

    private val _searchList = MutableStateFlow<Resource<List<NetworkArticle>>>(Resource.loading())
    val searchList : StateFlow<Resource<List<NetworkArticle>>> = _searchList



    fun getSearchResult(queryFlow: StateFlow<String>)  {
        viewModelScope.launch {

            queryFlow.debounce(300)
                .distinctUntilChanged()
                .filter { query ->
                    if (query.isEmpty() || query.length < 3) {
                        _searchList.value = Resource.success(emptyList())
                        return@filter false
                    }else {
                        return@filter true
                    }
                }
                .flatMapLatest { query ->
                    return@flatMapLatest repository.getSearchResults(query).catch {
                        emitAll(flowOf(emptyList()))
                    }
                }.flowOn(Dispatchers.IO)
                .collect {
                      _searchList.value = Resource.success(it)
                }

        }

    }


}