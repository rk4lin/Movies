package com.roman_kalinin.movies.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Movies")
data class Movies(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val imageUrl: String,
    val category: String,
    val desc: String
)

@Entity
data class UniqueIdentityForMoovies(
    val id: Long
)
