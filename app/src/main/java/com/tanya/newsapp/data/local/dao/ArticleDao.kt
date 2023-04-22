package com.tanya.newsapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tanya.newsapp.data.local.entities.ArticleEntity

@Dao
interface ArticleDao {

    @Insert
    suspend fun insertArticle(articleList: List<ArticleEntity>)

    @Query("Select * from article where category = (:category) and category_id = (:categoryId)")
    fun getArticles(category: String, categoryId: String): PagingSource<Int, ArticleEntity>

    @Query("Select last_modified From article Order By last_modified DESC LIMIT 1")
    fun getLastModified() : Long?

    @Query("DELETE FROM article")
    suspend fun deleteArticles()
}