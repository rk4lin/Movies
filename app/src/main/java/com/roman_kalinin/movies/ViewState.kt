package com.roman_kalinin.movies

sealed class ViewState {
    object Loaded: ViewState()
    object NoData: ViewState()

    data class Movies(
        val data: List<Movie>?
    ): ViewState()
}