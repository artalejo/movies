package com.android.movies.repository.datasource

interface Identifiable<out Key> {
    fun getKey(): Key
}