package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.data.model.Language
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.ui.base.BaseViewModel
import com.tanya.newsapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LanguageViewModel(val repository: TopHeadlineRepository,
                        private val dispatcherProvider: DispatcherProvider) : BaseViewModel() {

    private var _langSrcList = MutableStateFlow<UiState<List<Language>>>(UiState.Loading)

    val langSrcList : StateFlow<UiState<List<Language>>> = _langSrcList

    init{
        fetchLanguages()
    }

    private fun fetchLanguages() {
      viewModelScope.launch(dispatcherProvider.main) {
          repository.getLanguages()
              .flowOn(dispatcherProvider.default)
              .catch {
                  e -> _langSrcList.value = UiState.Error(e.toString())
              }
              .collect{
                  _langSrcList.value = UiState.Success(it)
              }
      }
    }
}