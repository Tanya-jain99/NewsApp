package com.tanya.newsapp.utils

import androidx.appcompat.widget.SearchView
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.data.model.ArticleEntity
import com.tanya.newsapp.data.model.NetworkArticle
import com.tanya.newsapp.data.model.Source
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

 fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {

    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })

    return query

}

fun NetworkArticle.asEntity(category: String, categoryId: String) =
    ArticleEntity(
        id = 0,
        title = title,
        description = description,
        url = url,
        urlToImage = imageUrl,
        category = category,
        categoryId =  categoryId
    )

fun ArticleEntity.asModel() = Article(
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    source = Source()
)

fun NetworkArticle.asModel() =
    Article(
        title = title,
        description = description,
        url = url,
        urlToImage = imageUrl,
        source = source
    )

