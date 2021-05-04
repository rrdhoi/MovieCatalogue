package com.jetpack.movieapp.data.source.remote.network

import com.jetpack.movieapp.data.source.remote.response.DetailMovieResponse
import com.jetpack.movieapp.data.source.remote.response.NowPlayingResponse
import com.jetpack.movieapp.data.source.remote.response.UpComingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String
    ): Call<NowPlayingResponse>

    @GET("movie/upcoming")
    fun getUpComing(
        @Query("api_key") apiKey: String
    ): Call<UpComingResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<DetailMovieResponse>
}