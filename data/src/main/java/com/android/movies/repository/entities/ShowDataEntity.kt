package com.android.movies.repository.entities

import com.google.gson.annotations.SerializedName

data class ShowDataEntity(
        var id: Long = 0L,
        @SerializedName("original_name") var originalName: String = "",
        @SerializedName( "genre_ids") var genres: List<Int> = arrayListOf(),
        var name: String = "",
        var popularity: Double = 0.0,
        @SerializedName("origin_country") var country: List<String> = arrayListOf(),
        @SerializedName("vote_count") var voteCount: Int = 0,
        @SerializedName("first_air_date") var firstAirDate: String = "",
        @SerializedName("backdrop_path") var backdropPath: String = "",
        @SerializedName("original_language") var originalLanguage: String = "",
        @SerializedName("vote_average") var voteAverage: Double = 0.0,
        var overview: String = "",
        @SerializedName("poster_path") var posterPath: String = ""
)