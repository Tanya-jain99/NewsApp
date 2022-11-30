package com.tanya.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.data.repository.TopHeadlineRepository
import com.tanya.newsapp.di.FragmentScope
import com.tanya.newsapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@FragmentScope
class TopHeadlineViewModel  (private val topHeadlineRepository: TopHeadlineRepository) : ViewModel() {

    private val _articleList = MutableStateFlow<Resource<List<Article>>>(Resource.loading())


    private lateinit var params : Map<String?, String?>

     fun fetchNews() : MutableStateFlow<Resource<List<Article>>> {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines(params)
                .catch { e ->
                    _articleList.value = Resource.error(e.toString())
                }
                .collect {
                    _articleList.value = Resource.success(it)
                }
        }
        return _articleList
    }

     fun loadData(data: Map<String?,String?>) {
        params = data
    }

}