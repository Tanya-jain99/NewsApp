package com.tanya.newsapp.data.api.models

import com.google.gson.annotations.SerializedName

data class NetworkArticle(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String? = "",
    @SerializedName("source")
    val source: Source
)