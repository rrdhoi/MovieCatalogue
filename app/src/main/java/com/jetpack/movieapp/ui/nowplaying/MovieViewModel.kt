package com.jetpack.movieapp.ui.nowplaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jetpack.movieapp.data.MovieNowPlaying
import com.jetpack.movieapp.data.source.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {

   fun getMovie(): LiveData<List<MovieNowPlaying>> = movieRepository.getAllNowPlaying()
}