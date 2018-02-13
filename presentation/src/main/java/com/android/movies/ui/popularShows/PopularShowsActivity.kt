package com.android.movies.ui.popularShows;

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.android.movies.R
import com.android.movies.ui.base.BaseActivity
import com.android.movies.ui.entities.ShowViewEntity
import com.android.movies.ui.utils.adapter.GenericAdapter
import com.android.movies.ui.utils.extensions.setup
import kotlinx.android.synthetic.main.activity_popular_shows.*
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject

class PopularShowsActivity : BaseActivity(), PopularShowsView {

    @Inject lateinit var presenter: PopularShowsPresenter
    override var layout = R.layout.activity_popular_shows
    @Inject lateinit var showsAdapter: GenericAdapter

    companion object {
        @JvmStatic fun getIntent(context: Context): Intent {
            return Intent(context, PopularShowsActivity::class.java)
        }
    }

    override fun onViewLoaded() {
        setUpToolbar()
        setUpRecyclerView()
    }

    private fun setUpToolbar() { shows_toolbar.toolbar_title.text = getString(R.string.popular_shows) }
    private fun setUpRecyclerView() {
        shows_recycler.setup(showsAdapter, LinearLayoutManager(this))
    }

    override fun fetchApiData() {
        presenter.onResume(hashMapOf())
        onLoading(shows_recycler, shows_progress_bar)
    }

    override fun populateWithCachedData() {
        showsAdapter.set(allItems, refresh = true)
    }

    override fun showPopularShows(shows: List<ShowViewEntity>) {
        allItems.addAll(shows)
        showsAdapter.set(allItems, refresh = true)
        onInfoRetrieved(shows_recycler, shows_progress_bar)
    }
}