package com.android.movies.ui.popularShows;

import com.android.movies.interactor.ShowsInteractor
import com.android.movies.network.ApiConstants.Companion.PAGE
import com.android.movies.ui.base.BasePresenter
import com.android.movies.ui.entities.mappers.toShowViewEntity
import javax.inject.Inject

class PopularShowsPresenter @Inject constructor(val view: PopularShowsView,
                                                private val showsInteractor: ShowsInteractor)
    : BasePresenter() {

    override fun onResume() =  getPopularShows()
    override fun onPause() = showsInteractor.cancelExecution()
    fun onLoadMoreShows(page: Int) = getPopularShows(page = page)

    private fun getPopularShows(page: Int = -1) {
        var apiParams = hashMapOf<String, String>()
        if (page >= 0) apiParams[PAGE]= page.toString()
        showsInteractor.execute(apiParams) {
            result ->
            result.success { value -> view.showPopularShows(value.map { it.toShowViewEntity() })}
            result.failure { exception -> exceptionHandler.notifyException(view, exception) }
        }
    }
}