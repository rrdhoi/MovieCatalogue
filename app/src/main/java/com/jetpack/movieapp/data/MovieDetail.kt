package com.jetpack.movieapp.data

data class MovieDetail(
    var movieId: Int?,
    var title: String?,
    var description: String?,
    var imagePath: String?,
    var bookmarked: Boolean? = false
)

data class ProCompanies(
    val name: String?,
    val imagePath: String?
)