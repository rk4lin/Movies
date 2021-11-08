package com.roman_kalinin.movies.viewmodel

import androidx.lifecycle.ViewModel
import com.roman_kalinin.movies.repository.MainRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val mainRepository: MainRepository) : ViewModel()  {

  private val _movieList = MutableStateFlow<ViewState>(ViewState.Loaded)
  val movieList : StateFlow<ViewState> = _movieList

    var job: Job? = null

    fun getAllMovies(){
        job = CoroutineScope(Dispatchers.IO).launch{

            val res = mainRepository.getData()
            if(res !=null){
                _movieList.emit(ViewState.Movies(res))
            }
            else{
                _movieList.emit(ViewState.Movies(listOf()))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}