package com.tanya.newsapp.data.api

import com.tanya.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor : Interceptor {

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder().addHeader("X-Api-Key", API_KEY).build()
        return chain.proceed(newRequest)
    }
}