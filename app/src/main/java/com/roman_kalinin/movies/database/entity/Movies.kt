package com.roman_kalinin.movies.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movies(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val category: String,
    val desc: String
)
