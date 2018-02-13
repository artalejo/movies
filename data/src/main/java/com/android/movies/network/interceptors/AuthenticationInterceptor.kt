package com.android.movies.network.interceptors

import com.android.movies.model.exceptions.NetworkException
import com.android.movies.network.ApiConstants
import com.android.movies.network.ConnectionChecker
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(private val connectionChecker: ConnectionChecker) : Interceptor {
    companion object {
        const val API_KEY = "api_key"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original : Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url()

        val urlWithKey = originalHttpUrl
                .newBuilder()
                .addQueryParameter(API_KEY, ApiConstants.API_KEY)
                .build()

        if (connectionChecker.thereIsConnectivity()) {
            val builder = chain.request().newBuilder().url(urlWithKey)
            return chain.proceed(builder.build())
        }
        throw NetworkException.NoInternetConnection()
    }
}