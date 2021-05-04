package com.jetpack.movieapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UpComingResponse(

    @field:SerializedName("results")
    val results: List<UpComing?>? = null
)

data class UpComing(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null
)

