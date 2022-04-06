package com.roman_kalinin.movies.di

import android.content.Context
import androidx.room.Room
import com.roman_kalinin.movies.database.MoviesDataBase
import com.roman_kalinin.movies.database.dao.MoviesDao
import com.roman_kalinin.movies.interactor.MoviesInteractor
import com.roman_kalinin.movies.network.MoviesApi
import com.roman_kalinin.movies.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MoviesModule {

    @Provides
    fun provideBaseUrl(): String = "https://howtodoandroid.com/"

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String): Retrofit {

        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        var okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    fun provideMainRepository(moviesApi: MoviesApi): MainRepository = MainRepository(moviesApi)

    @Provides
    fun provideMoviesInteractor(
        moviesRepository: MainRepository,
        dataBase: MoviesDataBase
    ): MoviesInteractor = MoviesInteractor(moviesRepository, dataBase)

    @Provides
    fun provideDatabase(moviesDatabase: MoviesDataBase) : MoviesDao = moviesDatabase.moviesDao()

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context : Context) : MoviesDataBase{
        return Room.databaseBuilder(context, MoviesDataBase::class.java, "moviebase").build()
    }
}