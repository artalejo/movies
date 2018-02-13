package com.android.movies.repository.entities.mappers

import com.android.movies.model.ShowInfo
import com.android.movies.network.ApiEndpoints
import com.android.movies.repository.entities.ShowDataEntity

fun ShowDataEntity.toShowInfo(): ShowInfo {
    return ShowInfo(this.id, this.originalName, this.genres, this.originalName, this.popularity,
            this.country, this.voteCount, this.firstAirDate, addBaseImagePath(this.backdropPath),
            this.originalLanguage, this.voteAverage, this.overview, addBaseImagePath(this.posterPath))
}

private fun addBaseImagePath(path: String) : String {
    return ApiEndpoints.BASE_IMAGE_URL + path
}