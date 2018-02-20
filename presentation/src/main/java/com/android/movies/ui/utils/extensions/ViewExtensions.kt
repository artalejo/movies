package com.android.movies.ui.utils.extensions

import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.android.movies.R
import com.android.movies.dependencyinjection.application.GlideApp
import com.android.movies.ui.utils.EndlessRecyclerOnScrollListener
import com.android.movies.ui.utils.adapter.GenericAdapter

private val TITLE_ANIM_DURATION : Long = 300
private val TITLE_ANIM_ALPHA  = 1f


/**
 * Loads an image from a specified url
 *
 * @param url image url
 * @param width sets the image width
 * @param height sets the image height
 * @param placeHolderResourceId placeholder that will be set by default
 */
fun ImageView.load(url: String, width: Int = 300, height: Int = 300,
                   placeHolderResourceId: Int = R.color.colorAccent) {

    GlideApp.with(this)
            .load(url)
            .override(width, height)
            .dontTransform()
            .error(R.drawable.ic_not_found)
            .placeholder(placeHolderResourceId)
            .into(this)
}

/**
 * Sets a view visibility to [View.VISIBLE]
 */
fun View.setVisible() {
    this.visibility = View.VISIBLE
}

/**
 * Sets a view visibility to [View.INVISIBLE]
 */

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Sets a view visibility to [View.GONE]
 */
fun View.setGone() {
    this.visibility = View.GONE
}

/**
 * Adds an error icon to an [EditText]
 *
 * @param errorResourceId error resource id to be displayed
 */
fun EditText.showErrorIcon(errorResourceId: Int = R.drawable.ic_error_red_24dp) {
    val dr = ContextCompat.getDrawable(context, errorResourceId)
    //add an error icon to yur drawable files
    dr?.let {
        dr.setBounds(0, 0, dr.intrinsicWidth, dr.intrinsicHeight)
        setCompoundDrawables(null, null, dr, null)
    }
}

fun RecyclerView.setup(adapter: GenericAdapter,
                       layoutManager: RecyclerView.LayoutManager,
                       itemDecoration: RecyclerView.ItemDecoration? = null,
                       scrollListener: RecyclerView.OnScrollListener? = null) {

    this.layoutManager = layoutManager
    itemDecoration?.let { this.addItemDecoration(it) }
    scrollListener?.let {
        this.clearOnScrollListeners()
        this.addOnScrollListener(it)
    }
    this.adapter = adapter
}

fun RecyclerView.setupWithEndless(adapter: GenericAdapter,
                                  layoutManager: RecyclerView.LayoutManager,
                                  itemDecoration: RecyclerView.ItemDecoration? = null,
                                  loadMore: (Int, Int) -> Unit) {

    this.layoutManager = layoutManager
    this.adapter = adapter

    itemDecoration?.let { this.addItemDecoration(it) }
    val customScrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int) {
            loadMore(page, totalItemsCount)
        }
    }
    this.clearOnScrollListeners()
    this.addOnScrollListener(customScrollListener)
}

fun RecyclerView.refreshScrollListener(layoutManager: RecyclerView.LayoutManager,
                                       loadMore: (page: Int, total: Int) -> Unit) {

    this.layoutManager = layoutManager
    val customScrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int) {
            loadMore(page, totalItemsCount)
        }
    }
    this.clearOnScrollListeners()
    this.addOnScrollListener(customScrollListener)
}


fun AppBarLayout.setParallaxBehaviour(toolbarTitle: TextView, actualTitle: String) {
    this.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
        if (verticalOffset <= -appBarLayout.totalScrollRange) {
            //Toolbar Collapsed
            toolbarTitle.text = actualTitle
            toolbarTitle.animate().alpha(TITLE_ANIM_ALPHA).duration = TITLE_ANIM_DURATION
        } else {
            //Toolbar Expanded
            toolbarTitle.text = " "
        }
    }
}