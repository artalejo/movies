package com.android.movies.ui.showDetail;

import com.android.movies.interactor.SimilarShowsInteractor
import com.android.movies.network.ApiConstants.Companion.PAGE
import com.android.movies.network.ApiConstants.Companion.SHOW_ID
import com.android.movies.ui.base.BasePresenter
import com.android.movies.ui.entities.mappers.toShowViewEntity
import javax.inject.Inject


class ShowDetailPresenter @Inject constructor(val view: ShowDetailView,
                                              private val similarShowsInteractor: SimilarShowsInteractor) : BasePresenter() {
    override fun onResume() {
        getSimilarShows()
    }
    fun onLoadMoreShows(page: Int) = getSimilarShows(page = page)

    private fun getSimilarShows(page: Int = -1) {
        var apiParams = hashMapOf<String, String>()
        apiParams[SHOW_ID] = view.getShowId().toString()
        if (page > 0) apiParams[PAGE]= page.toString()
        similarShowsInteractor.execute(apiParams) {
            result ->
            result.success { value -> view.showSimilarShows(value.map { it.toShowViewEntity() })}
            result.failure { exception -> exceptionHandler.notifyException(view, exception) }
        }
    }
    override fun onPause() = similarShowsInteractor.cancelExecution()
    fun onBackBtnPressed() =  view.onBackPressed()
}