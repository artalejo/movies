package com.android.movies.repository

import com.android.movies.Result

interface PreferencesRepository {
    fun get(key: String): Result<String, Exception>
    fun put(key: String, value: String): Result<String, Exception>
    fun delete(key: String): Result<String, Exception>
    fun clear(): Result<String, Exception>
}