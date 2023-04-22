package com.tanya.newsapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "title")
     val title : String,

    @ColumnInfo(name = "description")
     val description : String?,

    @ColumnInfo(name = "url")
     val url : String,

    @ColumnInfo(name = "url_to_image")
     val urlToImage : String?,

    @ColumnInfo(name = "category")
     val category : String,

    @ColumnInfo(name = "category_id")
    val categoryId : String,

    @ColumnInfo(name = "last_modified")
    val lastModified : Long
)