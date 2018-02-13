package com.android.movies.ui.utils.adapter

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View

class VerticalSpaceItemDecoration(val commonBottomSpace: Float = 0f,
                                  val context: Context,
                                  val handleLastItem: Boolean = false,
                                  val lastItemSpaceBottom: Float = 0f)

    : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        if (handleLastItem && parent.getChildAdapterPosition(view) != parent.adapter.itemCount - 1)
            outRect.bottom = commonBottomSpace.toInt()
        else if (handleLastItem) outRect.bottom = lastItemSpaceBottom.toInt()
        else outRect.bottom = commonBottomSpace.toInt()
    }
    fun convertPxToDP(spaceInPx: Int): Int {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spaceInPx.toFloat() , metrics).toInt()
    }
}