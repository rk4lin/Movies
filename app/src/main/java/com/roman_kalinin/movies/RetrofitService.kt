package com.roman_kalinin.movies

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface RetrofitService {
    @GET("movielist.json")
    suspend fun getAllMovies() : Response<List<Movie>>

    companion object{

        var retrofitService: RetrofitService? = null


        fun getInstance(): RetrofitService{

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