package com.roman_kalinin.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.roman_kalinin.movies.database.dao.MoviesDao
import com.roman_kalinin.movies.database.entity.Movies
import com.roman_kalinin.movies.view.MainActivity

@Database(entities = [Movies::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {

abstract fun moviesDao() : MoviesDao

    companion object{

        @Volatile private var instance: MoviesDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            MoviesDataBase::class.java, "movies.db")
            .build()
    }
}