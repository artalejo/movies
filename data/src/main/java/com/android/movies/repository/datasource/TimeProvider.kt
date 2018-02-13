package com.android.movies.repository.datasource

interface TimeProvider {
    fun currentTimeMillis(): Long
}