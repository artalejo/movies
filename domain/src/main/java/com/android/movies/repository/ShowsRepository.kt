package com.android.movies.repository

import com.android.movies.Result
import com.android.movies.model.ShowInfo

interface ShowsRepository {
    fun getShows(params: HashMap<String, String>): Result<List<ShowInfo>, *>
}