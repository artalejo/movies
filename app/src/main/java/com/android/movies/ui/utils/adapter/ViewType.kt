package com.android.movies.ui.utils.adapter

import android.os.Parcelable

interface ViewType: Parcelable {
    fun getViewType(): Int
}