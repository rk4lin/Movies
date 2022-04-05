package com.roman_kalinin.movies.repository

import android.util.Log
import com.roman_kalinin.movies.MoviesApplication
import com.roman_kalinin.movies.database.MoviesDataBase
import com.roman_kalinin.movies.database.entity.Movies
import com.roman_kalinin.movies.domain.Movie
import com.roman_kalinin.movies.network.MoviesApi
import com.roman_kalinin.movies.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

class MainRepository @Inject constructor(
    private val moviesApi: MoviesApi
    ) {

    suspend fun getMovies() = moviesApi.getAllMovies()
}

