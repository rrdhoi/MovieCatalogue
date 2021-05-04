package com.jetpack.movieapp.data.source

import androidx.lifecycle.LiveData
import com.jetpack.movieapp.data.MovieDetail
import com.jetpack.movieapp.data.MovieNowPlaying
import com.jetpack.movieapp.data.MovieUpComing
import com.jetpack.movieapp.data.ProCompanies

interface MovieDataSource {
    fun getAllNowPlaying(): LiveData<List<MovieNowPlaying>>

    fun getAllUpComing(): LiveData<List<MovieUpComing>>

    fun getDetailMovie(movieId: Int): LiveData<MovieDetail>

    fun getAllProCompanies(movieId: Int): LiveData<List<ProCompanies>>
}