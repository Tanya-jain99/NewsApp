package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.model.Country
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.ui.DispatcherProvider
import com.tanya.newsapp.ui.DispatcherProviderImpl

import com.tanya.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CountryViewModel(private val repository : TopHeadlineRepository,
                       private val dispatcherProvider: DispatcherProvider) : ViewModel() {

    private val _countrySrcList = MutableStateFlow<Resource<List<Country>>>(Resource.loading())

    val countrySrcList : StateFlow<Resource<List<Country>>> = _countrySrcList

    init{
       fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getCountries()
                .flowOn(dispatcherProvider.default)
                .catch { e ->
                    _countrySrcList.value = Resource.error(e.toString())
                }
                .collect {
                    _countrySrcList.value = Resource.success(it)
                }
        }
    }


}
