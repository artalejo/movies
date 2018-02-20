package com.android.movies.ui.showDetail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.movies.R
import com.android.movies.ui.utils.adapter.AdapterDelegate
import com.android.movies.ui.utils.adapter.ItemViewHolder
import com.android.movies.ui.utils.adapter.LoadingViewType
import com.android.movies.ui.utils.adapter.ViewType

class SimilarShowLoadingAdapterDelegate : AdapterDelegate<List<ViewType>>() {

    override fun onBindViewHolder(items: List<ViewType>, position: Int,
                                  holder: RecyclerView.ViewHolder, payloads: List<Any>) {}

    override fun isForViewType(items: List<ViewType>, position: Int) =
            items[position] is LoadingViewType

    override fun onCreateViewHolder(parent: ViewGroup): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_simiar_show_infinite_loader,
                                                               parent, false)
        return LoadingViewHolder(view!!)
    }

    class LoadingViewHolder(view: View) : ItemViewHolder(view)
}