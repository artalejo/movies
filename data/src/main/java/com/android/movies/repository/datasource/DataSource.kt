package com.android.movies.repository.datasource

import com.android.movies.Result
import com.android.movies.repository.implements
import com.android.movies.repository.query.Query
import java.util.*

interface CacheDataSource<Key, Value> : ReadableDataSource<Key, Value>, WritableDataSource<Key, Value> {

    fun isValid(value: Value): Boolean
}

interface WritableDataSource<in Key, Value> {

    fun deleteByKey(key: Key): Result<Unit, Exception> {
        return Result.Failure()
    }

    fun deleteAll(): Result<Unit, *> {
        return Result.Failure()
    }

    fun addOrUpdate(value: Value): Result<Value, Exception> {
        return Result.Failure()
    }

    fun addOrUpdateAll(values: Collection<Value>): Result<Collection<Value>, Exception> {
        return Result.Failure()
    }
}

interface ReadableDataSource<Key, out Value> {

    val queries: MutableSet<Query>

    fun getByKey(key: Key): Result<Value, Exception> {
        return Result.Failure()
    }

    fun getAll(): Result<Collection<Value>, Exception> {
        return Result.Failure()
    }

    fun queryAll(query: Class<*>, parameters: HashMap<String, *>? = null): Result<Collection<Value>, Exception> {
        queries.forEach {
            possibleQuery ->
            if (possibleQuery.implements(query)) {
                return possibleQuery.queryAll(parameters) as Result<Collection<Value>, *>
            }
        }
        return Result.Failure()
    }

    fun query(query: Class<*>, parameters: HashMap<String, *>? = null): Result<Value, Exception> {
        queries.forEach {
            possibleQuery ->
            if (possibleQuery.implements(query)) {
                return possibleQuery.query(parameters) as Result<Value, *>
            }
        }
        return Result.Failure()
    }

}
