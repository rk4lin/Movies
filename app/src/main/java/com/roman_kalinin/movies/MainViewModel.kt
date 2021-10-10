package com.roman_kalinin.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

import okhttp3.Dispatcher

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel()  {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Movie>>()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    var job: Job? = null

    val loading = MutableLiveData<Boolean>()

    fun getAllMovies(){
        job = CoroutineScope(Dispatchers.IO).launch{
            val response = mainRepository.getAllMovie()
            withContext(Dispatchers.Main){
              if(response.isSuccessful){
                  movieList.postValue(response.body())
                  loading.value = false
              }
                else{
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}