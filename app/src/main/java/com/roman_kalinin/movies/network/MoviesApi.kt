package com.roman_kalinin.movies.network

import com.roman_kalinin.movies.domain.Movie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface MoviesApi{
    @GET("movielist.json")
    suspend fun getAllMovies() : Response<List<Movie>>
}