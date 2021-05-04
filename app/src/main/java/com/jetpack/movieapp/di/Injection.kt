package com.jetpack.movieapp.di

import android.content.Context
import com.jetpack.movieapp.data.source.MovieRepository
import com.jetpack.movieapp.data.source.remote.RemoteDataSource

object Injection {
    fun providerRepository(context: Context): MovieRepository{
        val remoteDataSource = RemoteDataSource()

        return MovieRepository.getInstance(remoteDataSource)
    }
}