package com.android.movies.ui.showDetail

import android.content.Context
import android.content.Intent
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.android.movies.R
import com.android.movies.ui.base.BaseActivity
import com.android.movies.ui.entities.ShowViewEntity
import com.android.movies.ui.showDetail.adapter.SimilarShowsAdapterDelegate
import com.android.movies.ui.utils.InfiniteListener
import com.android.movies.ui.utils.adapter.GenericAdapter
import com.android.movies.ui.utils.adapter.ViewType
import com.android.movies.ui.utils.extensions.load
import com.android.movies.ui.utils.extensions.setParallaxBehaviour
import com.android.movies.ui.utils.extensions.setupWithEndless
import com.evernote.android.state.State
import kotlinx.android.synthetic.main.activity_show_detail.*
import kotlinx.android.synthetic.main.coordinator_toolbar.*
import kotlinx.android.synthetic.main.show_detail_header.view.*
import javax.inject.Inject


class ShowDetailActivity : BaseActivity(), InfiniteListener, ShowDetailView, SimilarShowsAdapterDelegate.SimilarShowClickListener {
    companion object Intent {
        private const val SHOW = "SHOW"
        @JvmStatic fun getIntent(context: Context, show: ShowViewEntity) =
                Intent(context, ShowDetailActivity::class.java).apply { putExtra(SHOW, show) }
    }

    @Inject lateinit var presenter: ShowDetailPresenter
    @Inject lateinit var similarShowsAdaper: GenericAdapter
    override var layout = R.layout.activity_show_detail
    @State var lastShowVisiblePos: Int = DEFAULT_VISIBLE_POSITION
    private lateinit var showInfo: ShowViewEntity
    override fun getBasePresenter() = presenter

    override fun onViewLoaded() {
        with(intent) {
            showInfo = getParcelableExtra(SHOW)
            if (showInfo == null) finish()
        }
        setUpToolbar()
        setUpRecyclerView()
        setUpShowInfo()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit()
    }

    private fun setUpToolbar() {
        val toolbar = show_detail_header.show_detail_toolbar as Toolbar
        toolbar.setOnClickListener{ presenter.onBackBtnPressed() }
        (show_detail_header as AppBarLayout).setParallaxBehaviour(toolbar,
                coordinator_toolbar_title, showInfo.originalName)
    }

    private fun setUpRecyclerView() {
        similarShowsAdaper.setClickListener(this)
        similarShowsAdaper.setInfiniteListener(this)
        similar_shows_recycler.setupWithEndless(similarShowsAdaper,
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false),
                loadMore = loadMoreData())
    }

    private fun setUpShowInfo(){
        with (showInfo) {
            show_detail_header.show_image.load(backdropPath)
            show_title.text = originalName
            show_date.text = firstAirDate
            show_overview.text = overview
            show_average.text = voteAverage.toString()
        }
    }

    private fun loadMoreData(): (Int, Int) -> Unit {
        return { page, totalItemsCount ->
            if (totalItemsCount.rem(LIMIT_PER_PAGE) == 0) {
                similarShowsAdaper.addLoadingView()
                presenter.onLoadMoreShows(page)
            }
        }
    }

    override fun onSimilarShowClicked(showId: Long, position: Int) {
        navigator.navigateToSwipableShows(this, allItems, position, showId)
    }

    // Infinite listener implementation
    override var limitPerPage = LIMIT_PER_PAGE
    override var lastItemVisiblePosition = lastShowVisiblePos
    override fun getShowId() = showInfo.id
    override fun showSimilarShows(shows: List<ShowViewEntity>) = similarShowsAdaper.addNewInfiniteItems(shows)
    override fun showInfoRetrieved() = onInfoRetrieved(similar_shows_recycler, similar_shows_progress_bar)
    override fun scrollToPosition() = similar_shows_recycler.scrollToPosition(lastShowVisiblePos)
    override fun changeInfiniteLoadingFinished(isInfiniteFinished: Boolean) {
        infiniteLoadingFinished = isInfiniteFinished
    }
    override fun addItemsToActivityItemList(newItems: List<ViewType>) { allItems.addAll(newItems) }
    override fun showEmptyView() {}
    override fun hideEmptyView() {}

}