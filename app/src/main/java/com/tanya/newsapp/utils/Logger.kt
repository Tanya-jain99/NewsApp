package com.tanya.newsapp.utils

interface Logger {

    fun e(tag: String, msg: String)

    fun d(tag: String, msg: String)

    fun i(tag: String, msg: String)
}