package com.tanya.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tanya.newsapp.data.local.dao.ArticleDao
import com.tanya.newsapp.data.local.dao.RemoteKeyDao
import com.tanya.newsapp.data.local.entities.ArticleEntity
import com.tanya.newsapp.data.local.entities.RemoteKeys
import javax.inject.Singleton

@Database(entities = [ArticleEntity::class, RemoteKeys::class] , version = 1)
@Singleton
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}