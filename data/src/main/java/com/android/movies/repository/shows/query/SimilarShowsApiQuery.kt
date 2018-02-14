package com.android.movies.repository.shows.query

import com.android.movies.Result
import com.android.movies.network.ApiConstants.Companion.SHOW_ID
import com.android.movies.network.getErrorException
import com.android.movies.network.services.ShowsService
import com.android.movies.repository.entities.ShowDataEntity
import com.android.movies.repository.query.Query
import retrofit2.Retrofit
import javax.inject.Inject

class SimilarShowsApiQuery @Inject constructor(private val retrofit: Retrofit) : Query {

    override fun queryAll(parameters: HashMap<String, *>?, queryable: Any?)
            : Result<Collection<ShowDataEntity>, *> {
        parameters?.let {
            val params = parameters as HashMap<String, String>
            val showID = parameters.remove(SHOW_ID) as String
            val showsService = retrofit.create(ShowsService::class.java)
            val response = showsService.getSimilarShows(showID, params).execute()
            return if (response.isSuccessful) Result.Success(response.body()!!.shows)
            else Result.Failure(response.getErrorException())
        }
        return Result.Failure()
    }
}