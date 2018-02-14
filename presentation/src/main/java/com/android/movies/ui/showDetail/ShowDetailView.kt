package com.android.movies.ui.showDetail;

import com.android.movies.ui.base.ErrorBaseView
import com.android.movies.ui.entities.ShowViewEntity


interface ShowDetailView : ErrorBaseView {
    fun showSimilarShows(shows: List<ShowViewEntity>)
    fun getShowId(): Long
    fun onBackPressed()
}