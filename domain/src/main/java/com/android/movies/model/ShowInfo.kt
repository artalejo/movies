package com.android.movies.model

data class ShowInfo(
        val id: Long,
        val originalName: String,
        val genres: List<Int>,
        val name: String,
        val popularity: Double,
        val country: List<String>,
        val voteCount: Int,
        val firstAirDate: String ,
        val backdropPath: String ,
        val originalLanguage: String,
        val voteAverage: Double,
        val overview: String,
        val posterPath: String
)
