package com.roman_kalinin.movies.viewmodel

import com.roman_kalinin.movies.domain.Movie

sealed class ViewState {
    object ConnectionError: ViewState()
    object Loaded: ViewState()
    object NoData: ViewState()

    data class Movies(
        val data: List<Movie>?
    ): ViewState()
}