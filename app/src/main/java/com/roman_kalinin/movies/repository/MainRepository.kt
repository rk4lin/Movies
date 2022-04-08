package com.roman_kalinin.movies.repository

import com.roman_kalinin.movies.network.MoviesApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val moviesApi: MoviesApi
    ) {
    suspend fun getMovies() = moviesApi.getAllMovies()
}

