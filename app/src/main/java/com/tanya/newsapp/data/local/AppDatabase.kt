package com.tanya.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tanya.newsapp.data.model.ArticleEntity
import javax.inject.Singleton

@Database(entities = [ArticleEntity::class] , version = 1)
@Singleton
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao() : ArticleDao
}