package com.android.movies.ui.utils.adapter

import android.widget.Filter

/**
 * GenericFilter
 *
 * Generic filter implement in order to filter items<[ViewType]> from [GenericAdapter]
 *
 * @property genericAdapter copy
 * @property allItems items to filter
 * @property filterToExecute function that filter single items and return verification
 * @property filteredItems items result to apply [filteredItems] in each [allItems] items
 * @constructor Creates an [GenericFilter].
 */
class GenericFilter(private val genericAdapter: GenericAdapter,
                    private var allItems: List<ViewType>,
                    private val filterToExecute: (it: ViewType, query: String) -> Boolean) : Filter() {

    private val filteredItems: ArrayList<ViewType> = arrayListOf()

    fun updateAllItems(allItems: List<ViewType>) {
        this.allItems = allItems
    }

    override fun performFiltering(constraint: CharSequence?) : FilterResults {
        filteredItems.clear()
        val results = FilterResults()

        if (constraint.isNullOrBlank()) {
            filteredItems.addAll(allItems)
        } else {
            val query = constraint.toString().toLowerCase().trim()
            allItems.filterTo(filteredItems) { filterToExecute(it, query) }
        }
        results.values = filteredItems
        results.count = filteredItems.size
        return results
    }

    override fun publishResults(p0: CharSequence?, results: FilterResults?) {
        if (results != null) {
            genericAdapter.publishFilteredResults(results.values as List<ViewType>)
        }
    }
}