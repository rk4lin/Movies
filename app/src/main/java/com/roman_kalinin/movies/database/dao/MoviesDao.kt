package com.roman_kalinin.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.roman_kalinin.movies.domain.Movie
import com.roman_kalinin.movies.viewmodel.ViewState

@Dao
interface MoviesDao {
    @Query("select * from movies")
    fun getMovies() : List<Movie>

    @Insert
    fun inserMovies(movies: List<Movie>)
}