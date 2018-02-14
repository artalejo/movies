package com.android.movies.ui.showDetail.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.movies.R
import com.android.movies.ui.entities.ShowViewEntity
import com.android.movies.ui.utils.adapter.AdapterDelegate
import com.android.movies.ui.utils.adapter.BaseListener
import com.android.movies.ui.utils.adapter.ItemViewHolder
import com.android.movies.ui.utils.adapter.ViewType
import com.android.movies.ui.utils.extensions.load
import kotlinx.android.synthetic.main.item_show.view.*

class SimilarShowsAdapterDelegate : AdapterDelegate<List<ViewType>>() {

    interface SimilarShowClickListener: BaseListener {
        fun onSimilarShowClicked(showInfo: ShowViewEntity)
    }

    override fun isForViewType(items: List<ViewType>, position: Int) =
            items[position] is ShowViewEntity

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_similar_show, parent, false)
        return ShowViewHolder(view, listener, context)
    }

    override fun onBindViewHolder(items: List<ViewType>, position: Int, holder: RecyclerView.ViewHolder, payloads: List<Any>) {
        (holder as? ShowViewHolder)?.bind(items[position] as ShowViewEntity)
    }

    class ShowViewHolder(view: View, val listener: BaseListener?,
                         val context: Context) : ItemViewHolder(view) {

        fun bind(showInfo: ShowViewEntity) {
            with(showInfo) {
                showInfo.let {
                    itemView.show_image.load(backdropPath)
                    itemView.show_name.text = originalName
                }

                itemView.setOnClickListener {
                    listener?.let { (it as SimilarShowClickListener).onSimilarShowClicked(showInfo) }
                }
            }
        }
    }
}