package com.jetpack.movieapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jetpack.movieapp.data.MovieDetail
import com.jetpack.movieapp.data.ProCompanies
import com.jetpack.movieapp.data.source.MovieRepository

class DetailMovieViewModel(private val mMovieRepository: MovieRepository): ViewModel() {
    var movieId: Int = 0

    fun setSelectedMovie(movieId: Int) {
        this.movieId = movieId
    }

    fun getMovie(): LiveData<MovieDetail> = mMovieRepository.getDetailMovie(movieId)

    fun getProCompanies(): LiveData<List<ProCompanies>> = mMovieRepository.getAllProCompanies(movieId)
}