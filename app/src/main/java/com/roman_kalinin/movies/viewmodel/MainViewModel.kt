package com.roman_kalinin.movies.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman_kalinin.movies.MoviesApplication
import com.roman_kalinin.movies.interactor.MoviesInteractor
import com.roman_kalinin.movies.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    /* private val mainRepository: MainRepository*/
    private val interactor: MoviesInteractor
) : ViewModel() {

    private val _movieList = MutableStateFlow<ViewState>(ViewState.Loaded)
    val movieList: StateFlow<ViewState> = _movieList

    var job: Job? = null

    fun getAllMovies() {

        job?.cancel()

        job = viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = interactor.getMovies(fromCache = true)

                if (result != null) {
                    _movieList.emit(ViewState.Movies(result))
                } else {
                    _movieList.emit(ViewState.ErrorLoading)
                }
            }

        }

    }

    fun manualRefreshAndGetMovies(){
        job?.cancel()

        job = viewModelScope.launch {
            _movieList.emit(ViewState.Loaded)
            withContext(Dispatchers.IO){
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