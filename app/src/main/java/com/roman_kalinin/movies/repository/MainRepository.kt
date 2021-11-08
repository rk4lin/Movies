package com.roman_kalinin.movies.repository

import android.util.Log
import com.roman_kalinin.movies.domain.Movie
import com.roman_kalinin.movies.network.RetrofitService
import okio.IOException

class MainRepository(private val retrofitService: RetrofitService) {

    var movies: List<Movie>? = listOf()

    suspend fun getAllMovie() = retrofitService.getAllMovies()

    suspend fun getData() : List<Movie>?{
        try {
            val res  = retrofitService.getAllMovies()

            if(res.isSuccessful){
                return res.body()
            }
            else{
                return null
            }

        } catch (ex: IOException) {
            Log.e("MainRepositoty", ex.message!!)
        }
        return null
    }
}
