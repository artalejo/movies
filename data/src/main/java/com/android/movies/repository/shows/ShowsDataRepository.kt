package com.android.movies.repository.albums

import com.android.movies.Result
import com.android.movies.model.ShowInfo
import com.android.movies.repository.Repository
import com.android.movies.repository.ShowsRepository
import com.android.movies.repository.entities.ShowDataEntity
import com.android.movies.repository.entities.mappers.toShowInfo
import com.android.movies.repository.shows.query.ShowsApiQuery
import com.android.movies.repository.shows.query.SimilarShowsApiQuery
import javax.inject.Inject

class ShowsDataRepository @Inject constructor(showsApiDataSource: ShowsApiDataSource)
    : ShowsRepository, Repository<Unit, ShowDataEntity>() {

    init {
        readableDataSources.add(showsApiDataSource)
    }

    override fun getShows(params: HashMap<String, String>): Result<List<ShowInfo>, *> {
        val result = queryAll(ShowsApiQuery::class.java, params)
        return result.map { shows -> shows.map { it.toShowInfo() }}
    }

    override fun getSimilarShows(params: HashMap<String, String>): Result<List<ShowInfo>, *> {
        val result = queryAll(SimilarShowsApiQuery::class.java, params)
        return result.map { shows -> shows.map { it.toShowInfo() }}
    }
}