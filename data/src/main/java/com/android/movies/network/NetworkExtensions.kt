package com.android.movies.network

import com.android.movies.model.exceptions.NetworkException
import retrofit2.Response


inline fun <reified T: Any> Response<T>.getErrorException() : Exception {
    return when (this.code()) {
        401, 403 -> NetworkException.UnauthorizedException()
        500 -> NetworkException.ServerException()
        else -> Exception()
    }
}

fun String?.safe() = this ?: ""
fun Int?.safe() = this ?: 0
fun Double?.safe() = this ?: 0.0
fun Long?.safe() = this ?: 0L
fun Boolean?.safe() = this ?: false

fun List<Any>?.safe() = this ?: listOf()
fun Map<Any, Any>?.safe() = this ?: mapOf()