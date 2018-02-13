package com.android.movies.network.services

import com.android.movies.network.ApiEndpoints
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET

interface ShowsService {
    @GET(ApiEndpoints.POPULAR_SHOWS)
    fun getPopularShows() : Call<JSONObject>
}