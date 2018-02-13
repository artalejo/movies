package com.android.movies.network.responses

import com.google.gson.annotations.SerializedName

data class CommonErrorResponse(@SerializedName("errors") val error : Error)

data class Error(@SerializedName("code") var codes: List<String>,
                 @SerializedName("message") var messages: List<String>)