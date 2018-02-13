package com.android.movies.ui.popularShows;

import com.android.movies.ui.base.ErrorBaseView
import com.android.movies.ui.entities.ShowViewEntity


interface PopularShowsView : ErrorBaseView {
    fun showPopularShows(shows: List<ShowViewEntity>)
}