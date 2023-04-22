package com.tanya.newsapp.utils

import androidx.appcompat.widget.SearchView
import com.tanya.newsapp.data.model.Article
import com.tanya.newsapp.data.local.entities.ArticleEntity
import com.tanya.newsapp.data.api.models.NetworkArticle
import com.tanya.newsapp.data.api.models.Source
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
        title = title,
        description = description,
        url = url,
        urlToImage = imageUrl,
        category = category,
        categoryId =  categoryId,
        lastModified = System.currentTimeMillis()
    )

fun ArticleEntity.asModel() = Article(
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage
)

