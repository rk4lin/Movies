package com.roman_kalinin.movies

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllMovie() = retrofitService.getAllMovies()
}