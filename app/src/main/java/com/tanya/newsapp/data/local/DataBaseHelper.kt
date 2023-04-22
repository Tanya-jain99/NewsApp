package com.tanya.newsapp.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.tanya.newsapp.data.local.entities.ArticleEntity
import com.tanya.newsapp.data.local.entities.RemoteKeys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseHelper @Inject constructor(private val appDatabase: AppDatabase) {

    fun getArticles(category: String, categoryId: String): PagingSource<Int, ArticleEntity> {
        return appDatabase.articleDao().getArticles(category, categoryId)
    }
    suspend fun getRemoteKeys(id: String, category: String, categoryValue: String): RemoteKeys {
        return appDatabase.remoteKeyDao().getRemoteKeys(id, category, categoryValue)
    }
    suspend fun insertArticle(articleList: List<ArticleEntity>) {
        appDatabase.articleDao().insertArticle(articleList)
    }
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>) {
        appDatabase.remoteKeyDao().addAllRemoteKeys(remoteKeys)
    }
    suspend fun clearArticles() {
        appDatabase.articleDao().deleteArticles()
    }
    suspend fun clearRemoteKeys() {
        appDatabase.remoteKeyDao().deleteAllRemoteKeys()
    }

    fun lastModified() : Long?{
      return appDatabase.articleDao().getLastModified()
    }
    suspend fun <R> runAsTransaction(block: suspend () -> R): R {
        return appDatabase.withTransaction(block)
    }
}