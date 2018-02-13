package com.android.movies.repository.datasource

import javax.inject.Inject

class SystemTimeProvider @Inject constructor() : TimeProvider {
    override fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}

