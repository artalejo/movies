package com.android.movies.ui.similarShows;

import com.android.movies.R
import com.android.movies.ui.base.BaseActivity
import com.android.movies.ui.base.BasePresenter
import com.android.movies.ui.entities.ShowViewEntity
import javax.inject.Inject


class SimilarShowsActivity : BaseActivity(), SimilarShowsView {

    @Inject lateinit var presenter: SimilarShowsPresenter

    override var layout = R.layout.activity_similar_shows
    override fun getBasePresenter(): BasePresenter? = presenter

    override fun onViewLoaded() {

    }

    override fun showSimilarShows(shows: List<ShowViewEntity>) {
    }

}