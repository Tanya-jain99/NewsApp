package com.tanya.newsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    val prevPage: Int?,
    val nextPage:Int?,
    val category: String,
    val categoryValue: String
)
