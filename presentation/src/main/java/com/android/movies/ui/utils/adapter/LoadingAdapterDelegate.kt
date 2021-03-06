package com.android.movies.ui.utils.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.movies.R

/**
 * This [AdapterDelegate] will be needed for the [GenericAdapter] for providing loaders
 * when dealing with infinite lists
 */
class LoadingAdapterDelegate : AdapterDelegate<List<ViewType>>() {

    override fun onBindViewHolder(items: List<ViewType>, position: Int,
                                  holder: RecyclerView.ViewHolder, payloads: List<Any>) {}

    override fun isForViewType(items: List<ViewType>, position: Int) =
            items[position] is LoadingViewType

    override fun onCreateViewHolder(parent: ViewGroup): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_infinite_loader,
                                                               parent, false)
        return LoadingViewHolder(view!!)
    }

    class LoadingViewHolder(view: View) : ItemViewHolder(view)
}