package com.android.movies.repository.albums

import com.android.movies.dependencyinjection.qualifier.ShowsApiQueries
import com.android.movies.repository.datasource.ReadableDataSource
import com.android.movies.repository.entities.ShowDataEntity
import com.android.movies.repository.query.Query
import javax.inject.Inject


class ShowsApiDataSource @Inject constructor(@ShowsApiQueries override val queries: MutableSet<Query>)
    : ReadableDataSource<Unit, ShowDataEntity>