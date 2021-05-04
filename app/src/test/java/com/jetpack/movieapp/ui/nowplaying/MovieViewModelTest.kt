package com.jetpack.movieapp.ui.nowplaying

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
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getMovie() {
       runBlocking {
           val dummyMovie = DataDummy.generateDummyMovie()
           val movies = MutableLiveData<List<MovieEntity>>()
           movies.value = dummyMovie

           `when`(movieRepository.getAllMovie()).thenReturn(movies)
           val movieEntities = viewModel.getMovie().value
           verify(movieRepository).getAllMovie()
           assertNotNull(movieEntities)
           assertEquals(10, movieEntities?.size)

           viewModel.getMovie().observeForever(observer)
           verify(observer).onChanged(dummyMovie)
       }
    }


}