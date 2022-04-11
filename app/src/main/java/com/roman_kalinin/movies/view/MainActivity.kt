package com.roman_kalinin.movies.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.roman_kalinin.movies.R
import com.roman_kalinin.movies.databinding.ActivityMainBinding
import com.roman_kalinin.movies.view.adpter.MainAdapter
import com.roman_kalinin.movies.viewmodel.MainViewModel
import com.roman_kalinin.movies.viewmodel.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
        binding.containerExecutorList.setOnRefreshListener {
           viewModel.manualRefreshAndGetMovies()
            binding.containerExecutorList.isRefreshing = false
       }
    }

    private fun subscribeChangeViewState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.movieList.collect {viewState->
                    when (viewState) {
                        is ViewState.Loaded -> {
                        }
                        is ViewState.Movies -> {
                            binding.recyclerview.visibility = View.VISIBLE
                            adapter.submitList(viewState.data)
                        }
                        is ViewState.ErrorLoading->{
                            binding.errorMessage.text = resources.getString(R.string.download_error)
                        }
                        is ViewState.ConnectionError -> {
                            binding.recyclerview.visibility = View.GONE
                            binding.errorMessage.visibility = View.VISIBLE
                            binding.errorMessage.text = resources.getString(R.string.no_connection_error)
                        }

                    }
                }
            }
        }

    }
}