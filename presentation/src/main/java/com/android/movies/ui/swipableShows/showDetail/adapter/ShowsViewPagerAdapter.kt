package com.android.movies.ui.swipableShows.showDetail.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.android.movies.ui.entities.ShowViewEntity
import com.android.movies.ui.swipableShows.showDetail.ShowDetailFragment
import com.android.movies.ui.utils.adapter.ViewType

class ShowsViewPagerAdapter(manager: FragmentManager, var items: List<ViewType>) :
        FragmentStatePagerAdapter(manager) {

    fun addNewItems(newItems: List<ViewType>, refresh: Boolean = false) {
        if (refresh && items.isNotEmpty()) return
        (items as ArrayList).addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return ShowDetailFragment.newInstance(items[position] as ShowViewEntity)
    }

    override fun getCount() = items.size
}