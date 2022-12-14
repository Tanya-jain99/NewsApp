package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.model.Language
import com.tanya.newsapp.data.repository.TopHeadlineRepository

import com.tanya.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class LanguageViewModel(val repository: TopHeadlineRepository) : ViewModel() {

    private var _langSrcList = MutableStateFlow<Resource<List<Language>>>(Resource.loading())

    val langSrcList : StateFlow<Resource<List<Language>>> = _langSrcList

    init{
        fetchLanguages()
    }

    private fun fetchLanguages() {
      viewModelScope.launch {
          repository.getLanguages()
              .flowOn(Dispatchers.Default)
              .catch {
                  e -> _langSrcList.value = Resource.error(e.toString())
              }
              .collect{
                  _langSrcList.value = Resource.success(it)
              }
      }
    }
}