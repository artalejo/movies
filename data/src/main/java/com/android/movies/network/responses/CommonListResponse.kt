package com.android.movies.network.responses

import com.google.gson.annotations.SerializedName

open class CommonListResponse {
    var page: Int = 0
    @SerializedName("total_results") var totalResults: Long = 0L
    @SerializedName("total_pages") var totalPages: Long = 0L
}