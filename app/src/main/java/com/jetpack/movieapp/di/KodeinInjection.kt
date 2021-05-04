package com.jetpack.movieapp.di

import android.app.Application
import com.jetpack.movieapp.data.source.MovieRepository
import com.jetpack.movieapp.data.source.remote.RemoteDataSource
import com.jetpack.movieapp.ui.detail.DetailMovieViewModel
import com.jetpack.movieapp.ui.nowplaying.MovieViewModel
import com.jetpack.movieapp.ui.upcoming.TvShowViewModel
import com.jetpack.movieapp.viewmodel.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class KodeinInjection : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@KodeinInjection))

        bind() from provider {
            RemoteDataSource()
        }

        bind() from provider {
            MovieRepository(instance())
        }

        bind() from provider {
            DetailMovieViewModel(instance())
        }

        bind() from provider {
            MovieViewModel(instance())
        }

        bind() from provider {
            TvShowViewModel(instance())
        }

        bind() from singleton {
            ViewModelFactory(instance())
        }
    }

}