package com.tanya.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tanya.newsapp.data.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert
    suspend fun insertArticle(articleList: List<ArticleEntity>)

    @Query("Select * from article where category = (:category) and categoryId = (:categoryId)")
    fun getArticles(category: String, categoryId: String): Flow<List<ArticleEntity>>

    @Query("DELETE FROM article")
    suspend fun deleteArticles()
}