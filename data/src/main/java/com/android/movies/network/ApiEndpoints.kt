package com.android.movies.network

interface ApiEndpoints {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POPULAR_SHOWS = "tv/popular"
    }
}