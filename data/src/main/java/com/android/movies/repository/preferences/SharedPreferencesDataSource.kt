package com.android.movies.repository.preferences

import android.content.Context
import android.content.SharedPreferences
import com.android.movies.Result
import com.android.movies.dependencyinjection.qualifier.ApplicationContext
import com.android.movies.dependencyinjection.qualifier.DefaultQueries
import com.android.movies.repository.datasource.CacheDataSource
import com.android.movies.repository.query.Query
import javax.inject.Inject

class SharedPreferencesDataSource  @Inject constructor(
        override @DefaultQueries val queries: MutableSet<Query>,
        @ApplicationContext context: Context)
    : CacheDataSource<String, String> {

    companion object {
        private val EMPTY_KEY = ""
    }

    private val preferences: SharedPreferences by lazy {
        val PREFERENCES_NAME = "SHARED_PREFS_" + context.packageName
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun isValid(value: String) =  true

    override fun getByKey(key: String): Result<String, Exception> {
        val value = get(key)
        return if (value == EMPTY_KEY) Result.Failure() else Result.Success(value)
    }

    fun putKey(key: String, value: String): Result<String, Exception> {
        val result = put(key, value)
        return if (result) Result.Success(key) else Result.Failure()
    }

    fun clear(): Result<String, Exception> {
        val success = preferences.edit().clear().commit()
        return if (success) Result.Success(EMPTY_KEY) else Result.Failure()
    }

    fun deleteKey(key: String): Result<String, Exception> {
        val success = delete(key)
        return if (success) Result.Success(key) else Result.Failure()
    }

    private fun get(key: String) = preferences.getString(key, EMPTY_KEY)
    private fun delete(key: String) = preferences.edit().remove(key).commit()
    private fun put(key: String, value: String) = preferences.edit().putString(key, value).commit()
}
