package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.model.Language
import com.tanya.newsapp.data.model.Source
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.NewsBaseFragmentScope
import com.tanya.newsapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@NewsBaseFragmentScope
class CategoryViewModel(private val topHeadlineRepository: TopHeadlineRepository): ViewModel() {

    private val _srcList = MutableStateFlow<Resource<List<Source>>>(Resource.loading())
    val srcList: StateFlow<Resource<List<Source>>> = _srcList


    init {
        fetchSources()
    }


    private fun fetchSources() {
        viewModelScope.launch {
            topHeadlineRepository.getNewsSources()
                .catch { e ->
                    _srcList.value = Resource.error(e.toString())
                }
                .collect {
                    _srcList.value = Resource.success(it)
                }
        }
    }

}