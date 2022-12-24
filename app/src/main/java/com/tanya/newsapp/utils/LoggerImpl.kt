package com.tanya.newsapp.utils

import android.util.Log

class LoggerImpl(private val tag: String) : Logger {

    override fun e( msg: String) {
        Log.e(tag, msg)
    }

    override fun d( msg: String) {
        Log.d(tag, msg)
    }

    override fun i(msg: String) {
        Log.i(tag, msg)
    }
}