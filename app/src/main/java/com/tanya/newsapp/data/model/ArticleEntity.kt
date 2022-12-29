package com.tanya.newsapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
     val id : Int,

    @ColumnInfo(name = "title")
     val title : String,

    @ColumnInfo(name = "desc")
     val description : String?,

    @ColumnInfo(name = "url")
     val url : String,

    @ColumnInfo(name = "urlToImage")
     val urlToImage : String?,

    @ColumnInfo(name = "category")
     val category : String,

    @ColumnInfo(name = "categoryId")
    val categoryId : String
)