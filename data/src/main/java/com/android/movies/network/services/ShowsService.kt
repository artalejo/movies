package com.android.movies.network.services

import com.android.movies.network.ApiEndpoints
import com.android.movies.network.responses.ShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ShowsService {
    @GET(ApiEndpoints.POPULAR_SHOWS)
    fun getPopularShows(@QueryMap params: Map<String, String>) : Call<ShowsResponse>

    @GET(ApiEndpoints.SIMILAR_SHOWS)
    fun getSimilarShows(@Path("tv_id") showID: String,
                        params: Map<String, String>) : Call<ShowsResponse>
}