package com.android.movies.ui.utils

import android.support.v4.view.ViewPager

fun ViewPager.onPageSelected(pageSelectedListener: (position: Int) -> Unit) {
    addOnPageChangeListener( object: ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) = pageSelectedListener(position)
    })
}