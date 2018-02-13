package com.android.movies.ui.popularShows;

import android.content.Context
import android.content.Intent
import com.android.movies.R
import com.android.movies.ui.base.BaseActivity
import javax.inject.Inject

class PopularShowsActivity : BaseActivity(), PopularShowsView {

    @Inject lateinit var presenter: PopularShowsPresenter
    override var layout = R.layout.activity_popular_shows

    companion object {
        @JvmStatic fun getIntent(context: Context): Intent {
            return Intent(context, PopularShowsActivity::class.java)
        }
    }

    override fun onViewLoaded() {

    }

}