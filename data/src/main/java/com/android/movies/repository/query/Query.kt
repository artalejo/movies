package com.android.movies.repository.query

import com.android.movies.Result

interface Query {

    fun queryAll(parameters: HashMap<String, *>? = null, queryable: Any? = null): Result<Collection<*>, *> {
        return Result.Failure()
    }
    fun query(parameters: HashMap<String, *>? = null, queryable: Any? = null): Result<*, *> {
        return Result.Failure()
    }

}