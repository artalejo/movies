package com.android.movies.ui.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.text.TextPaint
import android.text.style.URLSpan
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import com.android.movies.R
import com.android.movies.dependencyinjection.application.GlideApp
import com.android.movies.ui.utils.EndlessRecyclerOnScrollListener
import com.android.movies.ui.utils.adapter.GenericAdapter
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

private val NOT_FILLED = "Not filled"

/**
 * Loads an image from a specified url and adds a placeholder
 *
 * @param url image url
 * @param errorResourceId resource id in case the loading url fails
 * @param placeHolderResourceId placeholder that will be set by default
 */
fun ImageView.loadWithPlaceholders(url: String, errorResourceId: Int,
                   placeHolderResourceId: Int) {

    GlideApp.with(this)
            .load(url)
            .error(errorResourceId)
            .placeholder(placeHolderResourceId)
            .into(this)
}

/**
 * Loads a circular image from a specified url
 *
 * @param url image url
 * @param placeHolderResourceId placeholder that will be set by default
 * @param errorResourceId resource id in case the loading url fails
 */
fun ImageView.loadCircular(url: String, placeHolderResourceId: Int = R.drawable
        .rounded_image_placeholder, errorResourceId: Int = R.drawable.rounded_image_placeholder) {

    GlideApp.with(this)
            .load(url)
            .circleCrop()
            .error(errorResourceId)
            .placeholder(placeHolderResourceId)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
}

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
            .error(R.color.colorAccent)
            .placeholder(placeHolderResourceId)
            .into(this)
}

/**
 * Loads an image from a specified url and adds a signature with a date time for cache invalidation
 *
 * @param url image url
 * @param signature date time
 * @param placeHolderResourceId placeholder that will be set by default
 */
fun ImageView.loadWithSignature(url: String, signature: Long,
                               placeHolderResourceId: Int = R.color.colorAccent) {

    GlideApp.with(this)
            .load(url)
            .fitCenter()
            .signature(ObjectKey(signature))
            .error(R.color.colorAccent)
            .placeholder(placeHolderResourceId)
            .into(this)
}

/**
 * Generates a resized bitmap from a specified width and height
 *
 * @param newWidth desired width
 * @param newHeight desired height
 */
fun Bitmap.getResizedBitmap(newWidth: Int, newHeight: Int): Bitmap {
    val width = this.width
    val height = this.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    val resizedBitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
    this.recycle()
    return resizedBitmap
}

/**
 * Compresses a [Bitmap] into a [File]
 *
 * @param context
 * @param compressionPercentage compression percentage applied. By default is set to 70.
 *
 * @return [File] with the [Bitmap]
 */
fun Bitmap?.compressBitmapToFile(context: Context, compressionPercentage: Int = 70): File? {
    val TEMP_FILE_NAME = "temp.png"
    this?.let {
        val file = File(context.cacheDir, TEMP_FILE_NAME)
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, compressionPercentage, bos)
        val bitmapData = bos.toByteArray()

        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
        return file
    }
    return null
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

private fun TabLayout.updateTabsFont(context: Context, selectedFontId: Int, unselectedFontId: Int) {
    if (this.childCount <= 0) return
    val vg = this.getChildAt(0) as ViewGroup
    val selectedFont = ResourcesCompat.getFont(context, selectedFontId)
    val unselectedFont = ResourcesCompat.getFont(context, unselectedFontId)
    for (childIndex in 0 until vg.childCount) {
        val vgTab = vg.getChildAt(childIndex) as ViewGroup
        for (i in 0 until vgTab.childCount) {
            val tabViewChild = vgTab.getChildAt(i)
            if (tabViewChild is TextView)
                tabViewChild.typeface = if (childIndex == this.selectedTabPosition) selectedFont
                                        else unselectedFont
        }
    }
}

/**
 * Sets a font type to a [TableLayout]
 *
 * @param context
 * @param selectedFontId font resource id applied to the selected tab
 * @param unselectedFontId font resource id applied to the unselected tabs
 */
fun TabLayout.configureTabsFont(context: Context, selectedFontId: Int, unselectedFontId: Int) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {}
        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabSelected(tab: TabLayout.Tab?) {
            updateTabsFont(context, selectedFontId, unselectedFontId)
        }
    })
}

/**
 * Sets a method to be invoked when [SearchView.OnQueryTextListener] gets called
 *
 * @param onQueryChanged method to be invoked
 */
fun SearchView.setOnQueryTextChange(onQueryChanged: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(query: String): Boolean {
            onQueryChanged.invoke(query)
            return true
        }
    })
}

/**
 * Sets a method to be invoked once a spinner item gets selected via [AdapterView.OnItemSelectedListener]
 *
 * @param onItemSelected method to be invoked
 */
fun Spinner.onItemSelected(onItemSelected: (Int) -> Unit) {
    this.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }
}

/**
 * Shows a validation error to a [Spinner]
 *
 * @param errorToShow message to be shown
 */
fun Spinner.showValidationError(errorToShow : String = NOT_FILLED) {
    (this.getChildAt(0) as? TextView)?.error = errorToShow
}


/**
 * Removes error message from a [Spinner]
 *
 */
fun Spinner.invalidateValidationError() { (this.getChildAt(0) as? TextView)?.error = null }


/**
 * Loads and starts an animation to a [View]
 *
 * @param context
 * @param animation anim resource Id
 * @param setVisible sets the [View] visibility to [View.VISIBLE]
 * @param setGone sets the [View] visibility to [View.GONE]
 */
fun View.showAnimation(context:Context, animation: Int, setVisible: Boolean = false,
                       setGone: Boolean = false) {
    val animation = AnimationUtils.loadAnimation(context, animation)
    this.startAnimation(animation)
    if (setVisible) this.setVisible()
    if (setGone) this.setGone()
}

/**
 * Adds an error icon to an [EditText]
 *
 * @param errorResourceId error resource id to be displayed
 */
fun EditText.showErrorIcon(errorResourceId: Int = R.drawable.ic_error_red_24dp) {
    val dr = ContextCompat.getDrawable(context, errorResourceId)
    //add an error icon to yur drawable files
    dr.setBounds(0, 0, dr.intrinsicWidth, dr.intrinsicHeight)
    setCompoundDrawables(null, null, dr, null)
}

internal class URLSpanNoUnderline(url: String) : URLSpan(url) {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
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


fun AppBarLayout.setParallaxBehaviour(toolbar: Toolbar, toolbarTitle: TextView,
                                      actualTitle: String, pinColor: Int = R.color.colorAccent) {
    this.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
        if (verticalOffset <= -appBarLayout.totalScrollRange) {
            //Toolbar Collapsed
            toolbarTitle.text = actualTitle
            toolbar.setBackgroundColor(ContextCompat.getColor(context, pinColor))
        } else {
            //Toolbar Expanded
            toolbarTitle.text = " "
            toolbar.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }
    }
}