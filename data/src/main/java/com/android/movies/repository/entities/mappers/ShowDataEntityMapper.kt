package com.android.movies.repository.entities.mappers

import com.android.movies.model.ShowInfo
import com.android.movies.repository.entities.ShowDataEntity

fun ShowDataEntity.toShowInfo(): ShowInfo {
    return ShowInfo(this.id, this.originalName, this.genres, this.originalName, this.popularity,
            this.country, this.voteCount, this.firstAirDate, this.backdropPath,
            this.originalLanguage, this.voteAverage, this.overview, this.posterPath)
}