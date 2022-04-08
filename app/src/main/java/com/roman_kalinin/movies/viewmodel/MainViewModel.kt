package com.roman_kalinin.movies.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman_kalinin.movies.AppEvent
import com.roman_kalinin.movies.EventBus
import com.roman_kalinin.movies.MoviesApplication
import com.roman_kalinin.movies.interactor.MoviesInteractor
import com.roman_kalinin.movies.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val eventBus: EventBus,
    private val interactor: MoviesInteractor
) : ViewModel() {

    private val _movieList = MutableStateFlow<ViewState>(ViewState.Loaded)
    val movieList: StateFlow<ViewState> = _movieList

    var job: Job? = null

    fun getAllMovies() {

        job?.cancel()

        job = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = interactor.getMovies(fromCache = true)

                if (result != null && result.isNotEmpty()) {
                    _movieList.emit(ViewState.Movies(result))
                } else {
                    _movieList.emit(ViewState.ErrorLoading)
                }

                withContext(Dispatchers.Default){
                    eventBus.eventsFlow.filter {
                        it == AppEvent.NoConnection
                    }.collectLatest {
                        _movieList.emit(ViewState.ConnectionError)
                    }
                }

            }

        }

    }

    fun manualRefreshAndGetMovies() {
        job?.cancel()

        job = viewModelScope.launch {
            _movieList.emit(ViewState.Loaded)
            withContext(Dispatchers.IO) {
                val result = interactor.getMovies(fromCache = false)
                if (result != null) {
                    _movieList.emit(ViewState.Movies(result))
                } else {
                    _movieList.emit(ViewState.ErrorLoading)
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}