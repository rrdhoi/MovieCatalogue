package com.jetpack.movieapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jetpack.movieapp.data.MovieEntity
import com.jetpack.movieapp.data.source.MovieRepository
import com.jetpack.movieapp.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = DataDummy.generateDummyMovie()[0]
    private val movieId = dummyMovie.movieId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<MovieEntity>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieRepository)
        viewModel.setSelectedMovie(movieId)
    }

    @Test
    fun getMovie() {
        runBlocking {
            val movie = MutableLiveData<MovieEntity>()
            movie.value = dummyMovie

            `when`(movieRepository.getDetailMovie(movieId)).thenReturn(movie)
            val movieEntity = viewModel.getMovie().value as MovieEntity
            verify(movieRepository).getDetailMovie(movieId)

            assertNotNull(movieEntity)
            assertEquals(dummyMovie.movieId, movieEntity.movieId)
            assertEquals(dummyMovie.title, movieEntity.title)
            assertEquals(dummyMovie.description, movieEntity.description)
            assertEquals(dummyMovie.imagePath, movieEntity.imagePath)

            viewModel.getMovie().observeForever(observer)
            verify(observer).onChanged(dummyMovie)
        }
    }
}