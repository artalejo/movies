package com.android.movies.ui.popularShows;

import com.android.movies.interactor.ShowsInteractor
import com.android.movies.ui.base.BasePresenter
import com.android.movies.ui.entities.mappers.toShowViewEntity
import javax.inject.Inject

class PopularShowsPresenter @Inject constructor(val view: PopularShowsView,
                                                private val showsInteractor: ShowsInteractor)
    : BasePresenter() {

    fun onResume(params: HashMap<String, String>) = getShows(params)

    fun getShows(params: HashMap<String, String>) {
        showsInteractor.execute(params) {
            result ->
            result.success { value -> view.showPopularShows(value.map { it.toShowViewEntity() })}
            result.failure { exception -> exceptionHandler.notifyException(view, exception) }
        }
    }
}