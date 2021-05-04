package com.jetpack.movieapp.data.source.remote

import android.util.Log
import com.jetpack.movieapp.data.source.remote.network.ApiConfig
import com.jetpack.movieapp.data.source.remote.response.*
import com.jetpack.movieapp.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    fun getAllNowPlaying(callback: LoadNowPlaying) {
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().getNowPlaying("a653726e8a6c91ac70cdf09cf1308db5")
        client.enqueue(object : Callback<NowPlayingResponse> {
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onAllNowPlaying(response.body()?.results)
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.e("RemoteDataSource : ", "getNowPlaying Error : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getAllUpComing(callback: LoadUpComing) {
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().getUpComing("a653726e8a6c91ac70cdf09cf1308db5")
        client.enqueue(object : Callback<UpComingResponse> {
            override fun onResponse(
                call: Call<UpComingResponse>,
                response: Response<UpComingResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onAllUpComing(response.body()?.results)
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<UpComingResponse>, t: Throwable) {
                Log.e("RemoteDataSource : ", "getUpComing Error : ${t.message}")
                EspressoIdlingResource.decrement()
            }

        })
    }

    fun getDetailMovie(movieId: Int, callback: LoadDetailMovie) {
        EspressoIdlingResource.increment()

        val client =
            ApiConfig.getApiService().getDetailMovie(movieId, "a653726e8a6c91ac70cdf09cf1308db5")
        client.enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onDetailMovie(response.body())
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.e("RemoteDataSource : ", "getUpComing Error : ${t.message}")
                EspressoIdlingResource.decrement()
            }

        })
    }

    interface LoadNowPlaying {
        fun onAllNowPlaying(nowPlayingMovie: List<NowPlaying?>?)
    }

    interface LoadUpComing {
        fun onAllUpComing(upComingMovie: List<UpComing?>?)
    }

    interface LoadDetailMovie {
        fun onDetailMovie(detailMovie: DetailMovieResponse?)
    }

}