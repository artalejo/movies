package com.android.movies.repository

import com.android.movies.repository.query.Query

fun Query.implements(kInterface: Class<*>): Boolean {
    return kInterface.isAssignableFrom(this::class.java)
}