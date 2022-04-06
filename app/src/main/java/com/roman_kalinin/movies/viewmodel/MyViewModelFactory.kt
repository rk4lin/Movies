package com.roman_kalinin.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roman_kalinin.movies.interactor.MoviesInteractor
import com.roman_kalinin.movies.repository.MainRepository

class MyViewModelFactory constructor(private val interactor: MoviesInteractor): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            MainViewModel(this.interactor) as T

        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}