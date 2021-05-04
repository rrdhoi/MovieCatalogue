package com.jetpack.movieapp.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jetpack.movieapp.data.MovieUpComing
import com.jetpack.movieapp.data.source.MovieRepository

class TvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getTvShow(): LiveData<List<MovieUpComing>> = movieRepository.getAllUpComing()
}