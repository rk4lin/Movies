package com.roman_kalinin.movies.view

import android.content.Context
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.roman_kalinin.movies.databinding.ActivityMainBinding
import com.roman_kalinin.movies.network.RetrofitService
import com.roman_kalinin.movies.repository.MainRepository
import com.roman_kalinin.movies.view.adpter.MainAdapter
import com.roman_kalinin.movies.viewmodel.MainViewModel
import com.roman_kalinin.movies.viewmodel.MyViewModelFactory
import com.roman_kalinin.movies.viewmodel.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val adapter = MainAdapter()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerview.adapter = adapter
        subscribeChangeViewState()
        viewModel.getAllMovies()


    }

    private fun updateViewState(viewState: ViewState?) {
        if (viewState == null) return
        when (viewState) {
            is ViewState.NoData -> {
                // binding.noDataMessage.text = "Нет данных"
            }
            is ViewState.Loaded -> {
                //binding.loadDataMessage.text = "Загрузка данных"
            }
            is ViewState.Movies -> {
                binding.recyclerview.visibility = View.VISIBLE
                adapter.submitList(viewState.data)
            }
            is ViewState.ConnectionError -> {
                binding.recyclerview.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = "Соедиенние отсутствует"
            }

        }
    }

    private fun subscribeChangeViewState() {
        viewModel.movieList.asLiveData().observe(this, ::updateViewState)
    }
}