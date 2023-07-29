package com.binayshaw7777.readbud.utils

import android.util.Log


object Logger {

    fun debug(message: String) {
        Log.d("DEBUG MODE", message)
    }

    fun info(message: String) {
        Log.i("INFO MODE", message)
    }

    fun warn(message: String) {
        Log.w("INFO MODE", message)
    }
}