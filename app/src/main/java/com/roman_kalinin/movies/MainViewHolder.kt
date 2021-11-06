package com.roman_kalinin.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.roman_kalinin.movies.databinding.AdapterMovieBinding

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = AdapterMovieBinding.bind(itemView)

    fun bind(movie: Movie){
        binding.name.text = movie.name
        Glide.with(itemView.context).load(movie.imageUrl).into(binding.imageview)
    }

    companion object{
        fun create(parent: ViewGroup): MainViewHolder{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_movie, parent, false)
            return MainViewHolder(view)
        }
    }

}