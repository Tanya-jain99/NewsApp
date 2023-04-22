package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.data.model.Country
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.ui.base.BaseViewModel
import com.tanya.newsapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CountryViewModel(private val repository : TopHeadlineRepository,
                       private val dispatcherProvider: DispatcherProvider) : BaseViewModel() {

    private val _countrySrcList = MutableStateFlow<UiState<List<Country>>>(UiState.Loading)

    val countrySrcList : StateFlow<UiState<List<Country>>> = _countrySrcList

    init{
       fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getCountries()
                .flowOn(dispatcherProvider.default)
                .catch { e ->
                    _countrySrcList.value = UiState.Error(e.toString())
                }
                .collect {
                    _countrySrcList.value = UiState.Success(it)
                }
        }
    }


}
