package com.android.movies.model.exceptions

sealed class NetworkException : Exception() {
    class NoInternetConnection : NetworkException()
    class ServerException : NetworkException()
    class UnprocessableEntityException(val errorMessage: String?) : NetworkException()
    class UnauthorizedException : NetworkException()
}
