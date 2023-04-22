package com.tanya.newsapp.data.api

import com.tanya.newsapp.data.api.models.SourceResponse
import com.tanya.newsapp.data.api.models.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(@QueryMap options : Map<String, String>): TopHeadlinesResponse

    @GET("top-headlines/sources")
    suspend fun getNewsSources(): SourceResponse

    @GET("everything")
    suspend fun search(@Query("q") searchKey : String) : TopHeadlinesResponse

}