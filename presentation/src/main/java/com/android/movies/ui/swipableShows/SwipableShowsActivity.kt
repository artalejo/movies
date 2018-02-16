package com.android.movies.ui.swipableShows

import android.content.Context
import android.content.Intent
import com.android.movies.R
import com.android.movies.network.ApiConstants.Companion.DEFAULT_LIMIT_PER_PAGE
import com.android.movies.ui.base.BaseActivity
import com.android.movies.ui.entities.ShowViewEntity
import com.android.movies.ui.swipableShows.showDetail.adapter.ShowsViewPagerAdapter
import com.android.movies.ui.utils.adapter.ViewType
import com.android.movies.ui.utils.onPageSelected
import kotlinx.android.synthetic.main.activity_swipable_shows.*
import javax.inject.Inject


class SwipableShowsActivity : BaseActivity(), SwipableShowsView {

    companion object {
        const val VISIBLE_THRESHOLD = 5
        const val SHOWS = "SHOWS"
        const val SHOW_POSITION = "SHOW_POSITION"
        const val SHOW_ID = "SHOW_ID"
        @JvmStatic fun getIntent(context: Context, prefetchShows: ArrayList<ViewType>,
                                 positionToStart: Int, showId: Long): Intent {
            val intent = Intent(context, SwipableShowsActivity::class.java)
            intent.putParcelableArrayListExtra(SHOWS, prefetchShows)
            intent.putExtra(SHOW_POSITION, positionToStart)
            intent.putExtra(SHOW_ID, showId)
            return intent
        }
    }

    @Inject lateinit var presenter: SwipableShowsPresenter
    private lateinit var showsAdapter: ShowsViewPagerAdapter
    private lateinit var prefetchShows: List<ViewType>
    private var positionToStart: Int = 0
    private var showId: Long = 0L

    override var layout = R.layout.activity_swipable_shows
    override fun onViewLoaded() {
        with(intent) {
            prefetchShows = getParcelableArrayListExtra(SHOWS)
            positionToStart = getIntExtra(SHOW_POSITION, 0)
            showId = getLongExtra(SHOW_ID, 0L)
            if (prefetchShows == null) finish()
        }
        setupViewPager()
    }

    private fun setupViewPager() {
        val shows = arrayListOf<ViewType>()
        shows.addAll(prefetchShows)
        showsAdapter = ShowsViewPagerAdapter(supportFragmentManager, shows)
        shows_view_pager.adapter = showsAdapter
        shows_view_pager.currentItem = positionToStart
        shows_view_pager.onPageSelected {
            position ->
            if (allItems.size - position < VISIBLE_THRESHOLD) {
                presenter.loadMoreSimilarShows(calculatePage())
            }
        }
    }

    private fun calculatePage() = if (allItems.size > 0) (allItems.size / DEFAULT_LIMIT_PER_PAGE) else 0
    override fun populateWithCachedData() { showsAdapter.addNewItems(allItems, refresh = true) }
    override fun getShowId() = showId

    override fun showSimilarShows(newShows: List<ShowViewEntity>) {
        showsAdapter.addNewItems(newShows)
        allItems.addAll(newShows)
    }
}