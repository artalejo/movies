package com.android.movies.repository.entities.mappers

import com.android.movies.model.ShowInfo
import com.android.movies.network.ApiEndpoints
import com.android.movies.network.safe
import com.android.movies.repository.entities.ShowDataEntity

fun ShowDataEntity.toShowInfo(): ShowInfo {
    return ShowInfo(this.id.safe(), this.originalName.safe(), this.genres, this.originalName.safe(),
            this.popularity.safe(), this.country, this.voteCount.safe(), this.firstAirDate.safe(),
            addBaseImagePath(this.backdropPath),
            this.originalLanguage.safe(), this.voteAverage.safe(), this.overview.safe(),
            addBaseImagePath(this.posterPath))
}

private fun addBaseImagePath(path: String) : String {
    return ApiEndpoints.BASE_IMAGE_URL + path
}