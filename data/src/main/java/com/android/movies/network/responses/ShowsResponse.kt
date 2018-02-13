package com.android.movies.network.responses

import com.android.movies.repository.entities.ShowDataEntity
import com.google.gson.annotations.SerializedName

data class ShowsResponse(@SerializedName("results") var shows: List<ShowDataEntity>) :
        CommonListResponse()