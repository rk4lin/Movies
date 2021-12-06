package com.roman_kalinin.movies.repository

import android.util.Log
import com.roman_kalinin.movies.MoviesApplication
import com.roman_kalinin.movies.database.MoviesDataBase
import com.roman_kalinin.movies.database.entity.Movies
import com.roman_kalinin.movies.domain.Movie
import com.roman_kalinin.movies.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException


class MainRepository(private val retrofitService: RetrofitService) {

    private var movies: List<Movie>? = listOf()
    private var db = MoviesDataBase

    suspend fun getData(): List<Movie>? {
        getMovies()
        return movies
    }

    private suspend fun getMovies() {
        try {
            val res = retrofitService.getAllMovies()

            if (res.isSuccessful) {
                movies = res.body()
                if(movies != null){
              withContext(Dispatchers.IO){
                  requestAndSaveData(movies)
               }
              }
            }


        } catch (ex: IOException) {
            Log.e("MainRepositoty", ex.message!!)
        }
    }

    fun requestAndSaveData(m: List<Movie>?) {
        if(m != null){
           val insertData = m.map {
                Movies(
                    id = 0,
                    category = it.category,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    desc = it.desc
                )
            }
            db.invoke(MoviesApplication.applicationContext()).moviesDao().insertMovies(insertData)
        }

    }
}

