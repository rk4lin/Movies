package com.roman_kalinin.movies.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import com.roman_kalinin.movies.MoviesApplication
import com.roman_kalinin.movies.repository.MainRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _movieList = MutableStateFlow<ViewState>(ViewState.Loaded)
    val movieList: StateFlow<ViewState> = _movieList

    var job: Job? = null

    fun getAllMovies() {

        val check = isOnline(MoviesApplication.applicationContext())

        job = CoroutineScope(Dispatchers.IO).launch {
            if (check) {

                val res = mainRepository.getData()
                if (res != null) {
                    _movieList.emit(ViewState.Movies(res))
                } else {
                    _movieList.emit(ViewState.Movies(listOf()))
                }
            } else {
                _movieList.emit(ViewState.ConnectionError)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }
}