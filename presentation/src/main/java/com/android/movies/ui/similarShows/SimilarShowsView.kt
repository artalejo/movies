package com.android.movies.ui.similarShows;

import com.android.movies.ui.base.ErrorBaseView
import com.android.movies.ui.entities.ShowViewEntity


interface SimilarShowsView : ErrorBaseView {
    fun showSimilarShows(shows: List<ShowViewEntity>)
}