package com.jetpack.movieapp.ui.upcoming

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jetpack.movieapp.data.MovieEntity
import com.jetpack.movieapp.data.source.MovieRepository
import com.jetpack.movieapp.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(movieRepository)
    }

    @Test
    fun getTvShow() {
       runBlocking {
           val dummyMovie = DataDummy.generateDummyMovie()
           val movie = MutableLiveData<List<MovieEntity>>()
           movie.value = dummyMovie

           `when`(movieRepository.getAllTvShow()).thenReturn(movie)
           val movieEntity = viewModel.getTvShow().value
           verify(movieRepository).getAllTvShow()
           assertNotNull(movieEntity)
           assertEquals(10, movieEntity?.size)

           viewModel.getTvShow().observeForever(observer)
           verify(observer).onChanged(dummyMovie)
       }
    }
}