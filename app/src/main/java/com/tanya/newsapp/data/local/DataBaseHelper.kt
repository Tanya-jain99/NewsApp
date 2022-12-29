package com.tanya.newsapp.data.local

import com.tanya.newsapp.data.model.ArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseHelper @Inject constructor(private val appDatabase: AppDatabase) {


    fun getArticles(category: String, categoryId: String): Flow<List<ArticleEntity>>{
        return appDatabase.articleDao().getArticles(category, categoryId)
    }

    suspend fun insertArticle(articleList: List<ArticleEntity>) {
         appDatabase.articleDao().insertArticle(articleList)
    }

    suspend fun clearArticles() {
        appDatabase.articleDao().deleteArticles()
    }

}