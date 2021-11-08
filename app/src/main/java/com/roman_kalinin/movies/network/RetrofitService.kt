package com.roman_kalinin.movies.network

import com.roman_kalinin.movies.domain.Movie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
    @GET("movielist.json")
    suspend fun getAllMovies() : Response<List<Movie>>

    companion object{

        var retrofitService: RetrofitService? = null


        fun getInstance(): RetrofitService {

            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            var okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            if(retrofitService == null){
                val retrofit = Retrofit.Builder().baseUrl("https://howtodoandroid.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)

            }

            return retrofitService!!
        }
    }
}