package com.tanya.newsapp.data.model

data class Article(
    val title : String,
    val description : String? = "",
    val url : String,
    val urlToImage : String?
    )
