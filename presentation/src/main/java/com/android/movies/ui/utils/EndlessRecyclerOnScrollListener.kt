package com.android.movies.ui.utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Listener that handles the endless on scroll behaviour of a recycler view.
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener {

    private var visibleThreshold = 5
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 1
    private var isHorizontalEndless = false

    internal var layoutManager: RecyclerView.LayoutManager

    // Constructor that casts depending on linear layout type
    constructor(layoutManager: RecyclerView.LayoutManager) {
        when(layoutManager) {
            is LinearLayoutManager -> {
                this.layoutManager = layoutManager
                isHorizontalEndless = (layoutManager.orientation == LinearLayoutManager.HORIZONTAL)
            }
            is GridLayoutManager -> {
                this.layoutManager = layoutManager
                visibleThreshold *= layoutManager.spanCount
            }
            is StaggeredGridLayoutManager -> {
                this.layoutManager = layoutManager
                visibleThreshold *= layoutManager.spanCount
            }
            else -> this.layoutManager = layoutManager
        }
    }

    constructor(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
        isHorizontalEndless = (layoutManager.orientation == LinearLayoutManager.HORIZONTAL)
    }

    constructor(layoutManager: GridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView?, dx: Int, dy: Int) {
        val dimenToCheck = if (isHorizontalEndless) dx else dy
        if (dimenToCheck > 0) {
            var lastVisibleItemPosition = 0
            var totalItemCount = layoutManager.itemCount

            if (layoutManager is StaggeredGridLayoutManager) {
                val lastVisibleItemPositions = (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                // get maximum element within the list
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            } else if (layoutManager is LinearLayoutManager) {
                lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            } else if (layoutManager is GridLayoutManager) {
                lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }

            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex
                this.previousTotalItemCount = totalItemCount
                if (totalItemCount == 0) {
                    this.loading = true
                }
            }
            if (loading && totalItemCount > previousTotalItemCount) {
                loading = false
                previousTotalItemCount = totalItemCount
            }

            if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
                currentPage++
                onLoadMore(currentPage, totalItemCount)
                loading = true
            }
        }
    }

    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)
}