package com.android.movies.interactor

import com.android.movies.Result
import com.android.movies.model.ShowInfo
import com.android.movies.repository.ShowsRepository
import javax.inject.Inject

class ShowsInteractor @Inject constructor(val repository: ShowsRepository)
    : Interactor<List<ShowInfo>, HashMap<String, String>>() {

    override fun run(params: HashMap<String, String>): Result<List<ShowInfo>, *> {
        return repository.getShows(params)
    }
}