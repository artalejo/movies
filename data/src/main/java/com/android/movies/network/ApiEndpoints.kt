package com.android.movies.network

interface ApiEndpoints {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        const val POPULAR_SHOWS = "tv/popular"
        const val SIMILAR_SHOWS = "tv/{tv_id}/similar"
    }
}