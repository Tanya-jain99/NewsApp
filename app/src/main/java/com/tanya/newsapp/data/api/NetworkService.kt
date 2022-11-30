package com.tanya.newsapp.data.api

import com.tanya.newsapp.data.api.Networking.API_KEY
import com.tanya.newsapp.data.model.SourceResponse
import com.tanya.newsapp.data.model.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@QueryMap options : Map<String?, String?>): TopHeadlinesResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines/sources")
    suspend fun getNewsSources():SourceResponse


}