package com.jetpack.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jetpack.movieapp.data.source.MovieRepository
import com.jetpack.movieapp.ui.detail.DetailMovieViewModel
import com.jetpack.movieapp.ui.nowplaying.MovieViewModel
import com.jetpack.movieapp.ui.upcoming.TvShowViewModel

class ViewModelFactory(private val mMovieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(mMovieRepository) as T
            }

            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(mMovieRepository) as T
            }

            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(mMovieRepository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}