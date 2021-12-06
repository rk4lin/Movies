package com.roman_kalinin.movies

import android.app.Application
import android.content.Context

class MoviesApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MoviesApplication? = null

        fun applicationContext(): Context {
           return instance!!.applicationContext
        }
    }
}
