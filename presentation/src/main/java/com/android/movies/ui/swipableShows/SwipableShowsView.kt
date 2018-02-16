package com.android.movies.ui.swipableShows

import com.android.movies.ui.base.ErrorBaseView
import com.android.movies.ui.entities.ShowViewEntity

interface SwipableShowsView : ErrorBaseView {
    fun showSimilarShows(shows: List<ShowViewEntity>)
    fun getShowId(): Long
}