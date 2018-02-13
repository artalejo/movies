package com.android.movies.ui.entities

import android.os.Parcel
import android.os.Parcelable
import com.android.movies.ui.utils.adapter.AdapterConstants.VIEW_TYPE_SHOW
import com.android.movies.ui.utils.adapter.ViewType

data class ShowViewEntity(
        val id: Long,
        val originalName: String,
        val genres: List<Int>,
        val name: String,
        val popularity: Double,
        val country: List<String>,
        val voteCount: Int,
        val firstAirDate: String ,
        val backdropPath: String ,
        val originalLanguage: String,
        val voteAverage: Double,
        val overview: String,
        val posterPath: String
) : ViewType {

    override fun getViewType() = VIEW_TYPE_SHOW

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.createIntArray().toList(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.createStringArrayList(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(originalName)
        parcel.writeIntArray(genres.toIntArray())
        parcel.writeString(name)
        parcel.writeDouble(popularity)
        parcel.writeStringList(country)
        parcel.writeInt(voteCount)
        parcel.writeString(firstAirDate)
        parcel.writeString(backdropPath)
        parcel.writeString(originalLanguage)
        parcel.writeDouble(voteAverage)
        parcel.writeString(overview)
        parcel.writeString(posterPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShowViewEntity> {
        override fun createFromParcel(parcel: Parcel): ShowViewEntity {
            return ShowViewEntity(parcel)
        }

        override fun newArray(size: Int): Array<ShowViewEntity?> {
            return arrayOfNulls(size)
        }
    }
}
