package com.android.movies.ui.popularShows;

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.android.movies.R
import com.android.movies.ui.base.BaseActivity
import com.android.movies.ui.base.BasePresenter
import com.android.movies.ui.entities.ShowViewEntity
import com.android.movies.ui.popularShows.adapter.ShowsAdapterDelegate
import com.android.movies.ui.utils.InfiniteListener
import com.android.movies.ui.utils.adapter.GenericAdapter
import com.android.movies.ui.utils.adapter.ViewType
import com.android.movies.ui.utils.extensions.setupWithEndless
import com.evernote.android.state.State
import kotlinx.android.synthetic.main.activity_popular_shows.*
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject

class PopularShowsActivity : BaseActivity(), PopularShowsView,
        ShowsAdapterDelegate.ShowClickListener, InfiniteListener {

    @Inject lateinit var presenter: PopularShowsPresenter
    override var layout = R.layout.activity_popular_shows
    @State var lastShowVisiblePos: Int = DEFAULT_VISIBLE_POSITION
    @Inject lateinit var showsAdapter: GenericAdapter
    override fun getBasePresenter(): BasePresenter? = presenter

    companion object {
        @JvmStatic fun getIntent(context: Context): Intent {
            return Intent(context, PopularShowsActivity::class.java)
        }
    }

    override fun onViewLoaded() {
        setUpToolbar()
        setUpRecyclerView()
        onLoading(shows_recycler, shows_progress_bar)
    }

    private fun setUpToolbar() { shows_toolbar.toolbar_title.text = getString(R.string.popular_shows) }
    private fun setUpRecyclerView() {
        showsAdapter.setClickListener(this)
        showsAdapter.setInfiniteListener(this)
        shows_recycler.setupWithEndless(showsAdapter, LinearLayoutManager(this),
                                        loadMore = loadMoreShows())
    }

    private fun loadMoreShows(): (Int, Int) -> Unit {
        return { page, totalItemsCount ->
            if (totalItemsCount.rem(LIMIT_PER_PAGE) == 0 && !infiniteLoadingFinished)  {
                showsAdapter.addLoadingView()
                presenter.onLoadMoreShows(page)
            }
        }
    }

    override fun populateWithCachedData() {
        showsAdapter.set(allItems, refresh = true)
        showInfoRetrieved()
    }

    override fun onShowClicked(showInfo: ShowViewEntity) {
        navigator.navigateToShowDetail(this, showInfo)
    }

    // Infinite listener implementation
    override var limitPerPage = LIMIT_PER_PAGE
    override var lastItemVisiblePosition = lastShowVisiblePos
    override fun showPopularShows(shows: List<ShowViewEntity>) = showsAdapter.addNewInfiniteItems(shows)
    override fun showInfoRetrieved() = onInfoRetrieved(shows_recycler, shows_progress_bar)
    override fun showEmptyView() = showEmptyView(empty_shows_view, shows_recycler)
    override fun hideEmptyView() = hideEmptyView(empty_shows_view, shows_recycler)
    override fun scrollToPosition() = shows_recycler.scrollToPosition(lastShowVisiblePos)
    override fun changeInfiniteLoadingFinished(isInfiniteFinished: Boolean) {
        infiniteLoadingFinished = isInfiniteFinished
    }
    override fun addItemsToActivityItemList(newItems: List<ViewType>) { allItems.addAll(newItems) }
}