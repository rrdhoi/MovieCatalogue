package com.jetpack.movieapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jetpack.movieapp.data.MovieDetail
import com.jetpack.movieapp.data.MovieNowPlaying
import com.jetpack.movieapp.data.MovieUpComing
import com.jetpack.movieapp.data.ProCompanies
import com.jetpack.movieapp.data.source.remote.RemoteDataSource
import com.jetpack.movieapp.data.source.remote.response.DetailMovieResponse
import com.jetpack.movieapp.data.source.remote.response.NowPlaying
import com.jetpack.movieapp.data.source.remote.response.UpComing

class MovieRepository private constructor(private val remoteDataSource: RemoteDataSource): MovieDataSource{

    companion object{
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(remoteData: RemoteDataSource): MovieRepository = instance ?: synchronized(this) {
            instance ?: MovieRepository(remoteData).apply { instance = this }
        }
    }

    override fun getAllNowPlaying(): LiveData<List<MovieNowPlaying>> {
        val movieResults = MutableLiveData<List<MovieNowPlaying>>()

        remoteDataSource.getAllNowPlaying(object : RemoteDataSource.LoadNowPlaying {
            override fun onAllNowPlaying(nowPlayingMovie: List<NowPlaying?>?) {
                val movieList = ArrayList<MovieNowPlaying>()

                if (nowPlayingMovie != null) {
                    for (response in nowPlayingMovie){
                        val movie = MovieNowPlaying(
                            response?.id,
                            response?.title,
                            response?.voteAverage,
                            response?.posterPath
                        )

                        movieList.add(movie)
                    }
                }

                movieResults.postValue(movieList)
            }

        })
        return movieResults
    }

    override fun getAllUpComing(): LiveData<List<MovieUpComing>> {
        val movieResults = MutableLiveData<List<MovieUpComing>>()

        remoteDataSource.getAllUpComing(object : RemoteDataSource.LoadUpComing {
            override fun onAllUpComing(upComingMovie: List<UpComing?>?) {
                val movieList = ArrayList<MovieUpComing>()

                if (upComingMovie != null) {
                    for (response in upComingMovie) {
                        val movie = MovieUpComing(
                            response?.id,
                            response?.title,
                            response?.voteAverage,
                            response?.posterPath
                        )

                        movieList.add(movie)
                    }
                }
                movieResults.postValue(movieList)
            }
        })

        return movieResults
    }

    override fun getDetailMovie(movieId: Int): LiveData<MovieDetail> {
        val movieResults = MutableLiveData<MovieDetail>()

        remoteDataSource.getDetailMovie(movieId, object: RemoteDataSource.LoadDetailMovie {
            override fun onDetailMovie(detailMovie: DetailMovieResponse?) {
                if (detailMovie != null){
                    val movie = MovieDetail(
                        detailMovie.id,
                        detailMovie.title,
                        detailMovie.overview,
                        detailMovie.posterPath,
                        false
                    )

                    movieResults.postValue(movie)
                }
            }
        })


        return movieResults
    }

    override fun getAllProCompanies(movieId: Int): LiveData<List<ProCompanies>> {
        val movieProCompanies = MutableLiveData<List<ProCompanies>>()

        remoteDataSource.getDetailMovie(movieId, object: RemoteDataSource.LoadDetailMovie {
            override fun onDetailMovie(detailMovie: DetailMovieResponse?) {
                val listProCompanies = ArrayList<ProCompanies>()

                if (detailMovie != null) {
                    for (item in detailMovie.productionCompanies!!){
                        val proCompany = ProCompanies(
                            item?.name,
                            item?.logoPath
                        )
                        listProCompanies.add(proCompany)
                    }
                }

                movieProCompanies.postValue(listProCompanies)
            }
        })

        return movieProCompanies
    }
}