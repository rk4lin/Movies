package com.roman_kalinin.movies.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.roman_kalinin.movies.database.entity.Movies
import com.roman_kalinin.movies.domain.Movie


@Dao
interface MoviesDao {

    @Query("select * from movies")
    fun getMovies() : List<Movies>?

    @Insert
    fun insertMovies(movies: List<Movies>)

    @Delete
    fun deleteMovies(movie: Movies)
}