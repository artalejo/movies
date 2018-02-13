package com.android.movies.repository.policy

enum class ReadPolicy {
    CACHE_ONLY,
    READABLE_ONLY,
    READ_ALL;

    fun useCache(): Boolean {
        return this == CACHE_ONLY || this == READ_ALL
    }

    fun useReadable(): Boolean {
       return this == READ_ALL || this == READABLE_ONLY
    }
}
