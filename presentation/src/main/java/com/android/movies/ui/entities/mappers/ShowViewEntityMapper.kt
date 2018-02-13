package com.android.movies.ui.entities.mappers

import com.android.movies.model.ShowInfo
import com.android.movies.ui.entities.ShowViewEntity

fun ShowInfo.toShowViewEntity(): ShowViewEntity {
    return ShowViewEntity(this.id, this.originalName, this.genres, this.originalName, this.popularity,
            this.country, this.voteCount, this.firstAirDate, this.backdropPath,
            this.originalLanguage, this.voteAverage, this.overview, this.posterPath)
}