package com.android.movies.ui.utils.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Filterable
import com.android.movies.ui.utils.InfiniteListener
import javax.inject.Inject

/**
 * Mather of dragons and cows
 *
 * This adapter provides generic functionality for infinite, filterable and sectioned
 * lists. When dealing with infinite lists ensure to provide some [AdapterDelegate] which responds
 * to [LoadingViewType] entities
 *
 * @see LoadingAdapterDelegate
 *
 * @constructor creates the adapter and adds all delegates in the [delegatesManager].
 *
 * @param delegates [AdapterDelegate]s that will hold the adapter.
 *
 * @property items data to be handled.
 * @property delegatesManager [AdapterDelegatesManager] that contains the list of
 * [AdapterDelegate] that the adapter must hold.
 * @property customFilter Gets setted with a [GenericFilter]
 *
 */
class GenericAdapter @Inject constructor(delegates: MutableSet<AdapterDelegate<List<ViewType>>>)
    : RecyclerView.Adapter<ItemViewHolder>(), Filterable {

    private var items: List<ViewType> = arrayListOf()
    private val delegatesManager = AdapterDelegatesManager<List<ViewType>>()
    private lateinit var customFilter: GenericFilter
    private val DEFAULT_VISIBLE_POSITION = -1
    private var isLoading = false
    private var infiniteListener: InfiniteListener? = null

    init { delegatesManager.addAllDelegates(delegates) }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
            delegatesManager.onBindViewHolder(items, position, holder)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
        delegatesManager.onCreateViewHolder(parent!!, viewType) as ItemViewHolder

    override fun getItemViewType(position: Int) = delegatesManager.getItemViewType(items, position)

    override fun getItemCount() = items.size

    fun setInfiniteListener(infiniteListener: InfiniteListener) {
        this.infiniteListener = infiniteListener
    }

    /**
     * Sets a list of listeners to each delegate. Each listener must extend
     * from [BaseListener]
     */
    fun setMultipleListeners(listeners: List<BaseListener>) { delegatesManager.setListeners(listeners) }
    fun setClickListener(listener: BaseListener) { delegatesManager.setClickListener(listener) }

    fun getItemAt(position: Int): ViewType = items.elementAt(position)

    open fun addAll(newItems: List<ViewType>) {
        removeLoadingView()
        val start = items.size
        (items as ArrayList).addAll(newItems)
        notifyItemRangeChanged(start, items.size)
    }

    fun set(newItems: List<ViewType>) {
        (items as ArrayList).clear()
        (items as ArrayList).addAll(newItems)
        notifyDataSetChanged()
    }

    fun publishFilteredResults(filteredItems: List<ViewType>) {
        (items as ArrayList).clear()
        (items as ArrayList).addAll(filteredItems)
        notifyDataSetChanged()
    }

    fun clear() {
        (items as ArrayList).clear()
        notifyDataSetChanged()
    }

    fun add(item: ViewType) {
        (items as ArrayList).add(item)
        notifyItemInserted(items.size - 1)
    }

    fun deleteItem(item: ViewType) {
        item.let {
            val index = items.indexOf(item)
            if (index >= 0) deleteItemAt(index)
        }
    }

    fun deleteItemAt(position: Int) {
        (items as ArrayList).removeAt(position)
        notifyItemRemoved(position)
    }

    fun setFilter(filter: GenericFilter) { customFilter = filter }

    override fun getFilter() : GenericFilter {
        if (customFilter != null) return customFilter
        return GenericFilter(this, arrayListOf(), { _, _ -> true })
    }

    /***                INFINITE LOADER METHODS              ***/

    fun addLoadingView() {
        if (!isLoading) {
            (items as ArrayList).add(LoadingViewType())
            notifyItemInserted(items.size - 1)
            isLoading = true
        }
    }

    private fun removeLoadingView() {
        val loadingViewPos = items.size - 1
        if (isLoadingViewAdded(loadingViewPos)) {
            (items as ArrayList).removeAt(loadingViewPos)
            notifyItemRemoved(loadingViewPos)
            isLoading = false
        }
    }

    fun addNewInfiniteItems(newItems: List<ViewType>) {
        if (newItems.isEmpty()) {
            if(items.isEmpty()){
                infiniteListener?.showInfoRetrieved()
                infiniteListener?.showEmptyView()
            } else {
                infiniteListener?.hideEmptyView()
                infiniteListener?.changeInfiniteLoadingFinished(true)
                removeLoadingView()
            }
        }
        else {
            infiniteListener?.hideEmptyView()
            refreshNewInfiniteItems(newItems)
            infiniteListener?.addItemsToActivityItemList(newItems)
        }
    }

    private fun refreshNewInfiniteItems(newItems: List<ViewType>) {
        if (infiniteListener != null && newItems.size < infiniteListener!!.limitPerPage)
            infiniteListener!!.changeInfiniteLoadingFinished(true)
        addAll(newItems)
        infiniteListener?.showInfoRetrieved()
        if (infiniteListener?.lastItemVisiblePosition != DEFAULT_VISIBLE_POSITION)
            infiniteListener?.scrollToPosition()
    }

    private fun isLoadingViewAdded(loadingViewPos: Int) : Boolean {
        return items.isNotEmpty() && isLoading &&
                items[loadingViewPos].getViewType() == AdapterConstants.VIEW_TYPE_LOADING
    }
}