package com.roman_kalinin.movies.domain

import com.google.gson.annotations.SerializedName

data class Movie(
    val name: String,
    val imageUrl: String,
    val category: String,
    val desc: String
)