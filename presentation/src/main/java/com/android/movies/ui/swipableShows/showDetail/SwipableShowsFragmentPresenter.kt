package com.android.movies.ui.swipableShows.showDetail

import javax.inject.Inject


class SwipableShowsFragmentPresenter @Inject constructor(val view: ShowDetailFragView) {
    fun onBackBtnPressed() =  view.onBackPressed()
}