package com.tanya.newsapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Networking {

    private const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "48d92dfbe10b48c8b444f76332e984c0"

    fun create(): NetworkService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }

}
