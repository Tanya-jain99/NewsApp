package com.tanya.newsapp.utils

import android.util.Log

class LoggerImpl : Logger {

    override fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    override fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }
}