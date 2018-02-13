package com.android.movies.ui.utils.adapter

import android.os.Parcel
import android.os.Parcelable

class LoadingViewType() : ViewType {

    constructor(parcel: Parcel) : this()

    override fun getViewType() = AdapterConstants.VIEW_TYPE_LOADING
    override fun writeToParcel(parcel: Parcel, flags: Int) {}
    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<LoadingViewType> {
        override fun createFromParcel(parcel: Parcel): LoadingViewType {
            return LoadingViewType(parcel)
        }

        override fun newArray(size: Int): Array<LoadingViewType?> {
            return arrayOfNulls(size)
        }
    }
}