package com.roman_kalinin.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.roman_kalinin.movies.databinding.ActivityMainBinding
import com.roman_kalinin.movies.network.RetrofitService
import com.roman_kalinin.movies.repository.MainRepository
import com.roman_kalinin.movies.view.adpter.MainAdapter
import com.roman_kalinin.movies.viewmodel.MainViewModel
import com.roman_kalinin.movies.viewmodel.MyViewModelFactory
import com.roman_kalinin.movies.viewmodel.ViewState

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private val adapter = MainAdapter()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)
        subscribeChangeViewState()
        viewModel.getAllMovies()


    }

    private fun updateViewState(viewState: ViewState?){
        if(viewState ==  null) return
        when(viewState){
            is ViewState.NoData -> {
               // binding.noDataMessage.text = "Нет данных"
            }
            is ViewState.Loaded ->{
                //binding.loadDataMessage.text = "Загрузка данных"
            }
            is ViewState.Movies ->{
                binding.recyclerview.visibility = View.VISIBLE
                adapter.submitList(viewState.data)
            }

        }
    }

    private fun subscribeChangeViewState(){
        viewModel.movieList.asLiveData().observe(this, ::updateViewState)
    }
}