package com.android.movies.network

import com.android.movies.model.exceptions.NetworkException
import com.android.movies.network.responses.CommonErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

private val DATE_FORMAT_HMS = "%2d:%02d:%02d"
private val DATE_FORMAT_MS = "%02d:%02d"

inline fun <reified T: Any> Response<T>.getErrorException() : Exception {
    return when (this.code()) {
        401, 403 -> NetworkException.UnauthorizedException()
        422 -> NetworkException.UnprocessableEntityException(this.errorBody()!!.extractErrorMessage())
        500 -> NetworkException.ServerException()
        else -> Exception()
    }
}

fun ResponseBody.extractErrorMessage() : String? {
    try {
        val errorResponse = Gson().fromJson(this.string(), CommonErrorResponse::class.java)
        if (errorResponse.error.messages.isNotEmpty())
            return errorResponse.error.messages[0]
    } catch (e: Exception) { return null }
    return null
}

fun ResponseBody.extractErrorCode() : String? {
    try {
        val errorResponse = Gson().fromJson(this.string(), CommonErrorResponse::class.java)
        if (errorResponse.error.codes.isNotEmpty())
            return errorResponse.error.codes[0]
    } catch (e: Exception) { return null }
    return null
}

fun String?.safe() = this ?: ""
fun Int?.safe() = this ?: 0
fun Double?.safe() = this ?: 0.0
fun Long?.safe() = this ?: 0L
fun Boolean?.safe() = this ?: false

fun List<Any>?.safe() = this ?: listOf()
fun Map<Any, Any>?.safe() = this ?: mapOf()