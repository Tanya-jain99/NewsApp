package com.tanya.newsapp.data.api

import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor : Interceptor {

    companion object {
        private const val API_KEY = "48d92dfbe10b48c8b444f76332e984c0"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder().addHeader("X-Api-Key", API_KEY).build()
        return chain.proceed(newRequest)
    }
}