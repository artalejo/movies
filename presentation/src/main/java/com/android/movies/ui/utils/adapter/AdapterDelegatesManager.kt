package com.android.movies.ui.utils.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * This class is the element that ties [RecyclerView.Adapter] together with [ ].
 *
 *
 * So you have to add / register your [AdapterDelegate]s to this manager by calling [ ][.addDelegate]
 *
 *
 *
 *
 * Next you have to add this AdapterDelegatesManager to the [RecyclerView.Adapter] by calling
 * corresponding methods:
 *
 *  *  [.getItemViewType]: Must be called from [ ][RecyclerView.Adapter.getItemViewType]
 *  *  [.onCreateViewHolder]: Must be called from [ ][RecyclerView.Adapter.onCreateViewHolder]
 *  *  [.onBindViewHolder]: Must be called from [ ][RecyclerView.Adapter.onBindViewHolder]
 *
 *
 * You can also set a fallback [AdapterDelegate] by using [ ][.setFallbackDelegate] that will be used if no [AdapterDelegate] is
 * responsible to handle a certain view type. If no fallback is specified, an Exception will be
 * thrown if no [AdapterDelegate] is responsible to handle a certain view type
 *
 *
 * @param <T> The type of the datasource of the adapter
 * @author Hannes Dorfmann
</T> */
class AdapterDelegatesManager<T> {

    companion object {
        /**
         * This id is used internally to claim that the []
         */
        internal val FALLBACK_DELEGATE_VIEW_TYPE = Integer.MAX_VALUE - 1
        /**
         * Used internally for [.onBindViewHolder] as empty
         * payload parameter
         */
        private val PAYLOADS_EMPTY_LIST = emptyList<Any>()
    }

    /**
     * Map for ViewType to AdapterDelegate
     */
    protected var delegates: SparseArrayCompat<AdapterDelegate<T>> = SparseArrayCompat()
    protected var defaultFallbackDelegate: AdapterDelegate<T>? = null

    /**
     * Adds an [AdapterDelegate].
     * **This method automatically assign internally the view type integer by using the next
     * unused**
     *
     * Internally calls [.addDelegate] with
     * allowReplacingDelegate = false as parameter.
     *
     * @param delegate the delegate to add
     * @return self
     * @throws NullPointerException if passed delegate is null
     * @see .addDelegate
     * @see .addDelegate
     */
    fun addDelegate(delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        // algorithm could be improved since there could be holes,
        // but it's very unlikely that we reach Integer.MAX_VALUE and run out of unused indexes
        var viewType = delegates.size()
        while (delegates.get(viewType) != null) {
            viewType++
            if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
                throw IllegalArgumentException(
                        "Oops, we are very close to Integer.MAX_VALUE. It seems that there are no more free and unused view type integers left to add another AdapterDelegate.")
            }
        }
        return addDelegate(viewType, false, delegate)
    }

    /**
     * Joan decided to do this.
     */
    fun addAllDelegates(delegates: Set<AdapterDelegate<T>>?) {
        if (delegates == null) return

        for (delegate : AdapterDelegate<T> in delegates) {
            addDelegate(delegate)
        }
    }

    /**
     * Set the listeners for all adapter delegates in order to implement the click listeners.
     */
    fun setListeners(listeners: List<BaseListener>) {
        if (listeners.isEmpty()) return

        (0 until delegates.size())
                .map { delegates.get(it) }
                .forEach { it.setMultipleListeners(listeners) }
    }

    /**
     * Set the click listener for all adapter delegates in order to implement the click listeners.
     */
    fun setClickListener(listener: BaseListener) {
        (0 until delegates.size())
                .map { delegates.get(it) }
                .forEach { it.setClickListener(listener) }
    }

    /**
     * Adds an [AdapterDelegate] with the specified view type.
     *
     * Internally calls [.addDelegate] with
     * allowReplacingDelegate = false as parameter.
     *
     * @param viewType the view type integer if you want to assign manually the view type. Otherwise
     * use [.addDelegate] where a viewtype will be assigned manually.
     * @param delegate the delegate to add
     * @return self
     * @throws NullPointerException if passed delegate is null
     * @see .addDelegate
     * @see .addDelegate
     */
    fun addDelegate(viewType: Int,
                    delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        return addDelegate(viewType, false, delegate)
    }

    /**
     * Adds an [AdapterDelegate].
     *
     * @param viewType The viewType id
     * @param allowReplacingDelegate if true, you allow to replacing the given delegate any previous
     * delegate for the same view type. if false, you disallow and a [IllegalArgumentException]
     * will be thrown if you try to replace an already registered [AdapterDelegate] for the
     * same view type.
     * @param delegate The delegate to add
     * @throws IllegalArgumentException if **allowReplacingDelegate**  is false and an [ ] is already added (registered)
     * with the same ViewType.
     * @throws IllegalArgumentException if viewType is [.FALLBACK_DELEGATE_VIEW_TYPE] which is
     * reserved
     * @see .addDelegate
     * @see .addDelegate
     * @see .setFallbackDelegate
     */
    fun addDelegate(viewType: Int, allowReplacingDelegate: Boolean,
                    delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {

        if (delegate == null) {
            throw NullPointerException("AdapterDelegate is null!")
        }

        if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
            throw IllegalArgumentException("The view type = "
                    + FALLBACK_DELEGATE_VIEW_TYPE
                    + " is reserved for fallback adapter delegate (see setFallbackDelegate() ). Please use another view type.")
        }

        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw IllegalArgumentException(
                    "An AdapterDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered AdapterDelegate is "
                            + delegates.get(viewType))
        }

        delegates.put(viewType, delegate)

        return this
    }

    /**
     * Removes a previously registered delegate if and only if the passed delegate is registered
     * (checks the reference of the object). This will not remove any other delegate for the same
     * viewType (if there is any).
     *
     * @param delegate The delegate to remove
     * @return self
     */
    fun removeDelegate(delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {

        if (delegate == null) {
            throw NullPointerException("AdapterDelegate is null")
        }

        val indexToRemove = delegates.indexOfValue(delegate)

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove)
        }
        return this
    }

    /**
     * Removes the adapterDelegate for the given view types.
     *
     * @param viewType The Viewtype
     * @return self
     */
    fun removeDelegate(viewType: Int): AdapterDelegatesManager<T> {
        delegates.remove(viewType)
        return this
    }

    /**
     * Must be called from [RecyclerView.Adapter.getItemViewType]. Internally it scans all
     * the registered [AdapterDelegate] and picks the right one to return the ViewType integer.
     *
     * @param items Adapter's data source
     * @param position the position in adapters data source
     * @return the ViewType (integer). Returns [.FALLBACK_DELEGATE_VIEW_TYPE] in case that the
     * fallback adapter delegate should be used
     * @throws NullPointerException if no [AdapterDelegate] has been found that is
     * responsible for the given data element in data set (No [AdapterDelegate] for the given
     * ViewType)
     * @throws NullPointerException if items is null
     */
    fun getItemViewType(items: T, position: Int): Int {

        if (items == null) {
            throw NullPointerException("Items datasource is null!")
        }

        val delegatesCount = delegates.size()
        for (i in 0 until delegatesCount) {
            val delegate = delegates.valueAt(i)
            if (delegate.isForViewType(items, position)) {
                return delegates.keyAt(i)
            }
        }

        if (defaultFallbackDelegate != null) {
            return FALLBACK_DELEGATE_VIEW_TYPE
        }

        throw NullPointerException(
                "No AdapterDelegate added that matches position=$position in data source")
    }

    /**
     * This method must be called in [RecyclerView.Adapter.onCreateViewHolder]
     *
     * @param parent the parent
     * @param viewType the view type
     * @return The new created ViewHolder
     * @throws NullPointerException if no AdapterDelegate has been registered for ViewHolders
     * viewType
     */
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate = getDelegateForViewType(viewType) ?: throw NullPointerException("No AdapterDelegate added for ViewType " + viewType)

        val vh = delegate!!.onCreateViewHolder(parent) ?: throw NullPointerException(("ViewHolder returned from AdapterDelegate "
                + delegate
                + " for ViewType ="
                + viewType
                + " is null!"))
        return vh
    }

    /**
     * Must be called from[RecyclerView.Adapter.onBindViewHolder]
     *
     * @param items Adapter's data source
     * @param position the position in data source
     * @param viewHolder the ViewHolder to bind
     * @param payloads A non-null list of merged payloads. Can be empty list if requires full update.
     * @throws NullPointerException if no AdapterDelegate has been registered for ViewHolders
     * viewType
     */
    @JvmOverloads
    fun onBindViewHolder(items: T, position: Int,
                         viewHolder: RecyclerView.ViewHolder, payloads: List<*>? = PAYLOADS_EMPTY_LIST) {

        val delegate = getDelegateForViewType(viewHolder.itemViewType) ?: throw NullPointerException(("No delegate found for item at position = "
                + position
                + " for viewType = "
                + viewHolder.itemViewType))
        delegate!!.onBindViewHolder(items, position, viewHolder,
                payloads as List<Any>? ?: PAYLOADS_EMPTY_LIST)
    }

    /**
     * Must be called from [RecyclerView.Adapter.onViewRecycled]
     *
     * @param viewHolder The ViewHolder for the view being recycled
     */
    fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
        val delegate = getDelegateForViewType(viewHolder.itemViewType) ?: throw NullPointerException(("No delegate found for "
                + viewHolder
                + " for item at position = "
                + viewHolder.adapterPosition
                + " for viewType = "
                + viewHolder.itemViewType))
        delegate!!.onViewRecycled(viewHolder)
    }

    /**
     * Must be called from [RecyclerView.Adapter.onFailedToRecycleView]
     *
     * @param viewHolder The ViewHolder containing the View that could not be recycled due to its
     * transient state.
     * @return True if the View should be recycled, false otherwise. Note that if this method
     * returns `true`, RecyclerView *will ignore* the transient state of
     * the View and recycle it regardless. If this method returns `false`,
     * RecyclerView will check the View's transient state again before giving a final decision.
     * Default implementation returns false.
     */
    fun onFailedToRecycleView(viewHolder: RecyclerView.ViewHolder): Boolean {
        val delegate = getDelegateForViewType(viewHolder.itemViewType) ?: throw NullPointerException(("No delegate found for "
                + viewHolder
                + " for item at position = "
                + viewHolder.adapterPosition
                + " for viewType = "
                + viewHolder.itemViewType))
        return delegate!!.onFailedToRecycleView(viewHolder)
    }

    /**
     * Must be called from [RecyclerView.Adapter.onViewAttachedToWindow]
     *
     * @param viewHolder Holder of the view being attached
     */
    fun onViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        val delegate = getDelegateForViewType(viewHolder.itemViewType) ?: throw NullPointerException(("No delegate found for "
                + viewHolder
                + " for item at position = "
                + viewHolder.adapterPosition
                + " for viewType = "
                + viewHolder.itemViewType))
        delegate!!.onViewAttachedToWindow(viewHolder)
    }

    /**
     * Must be called from [RecyclerView.Adapter.onViewDetachedFromWindow]
     *
     * @param viewHolder Holder of the view being attached
     */
    fun onViewDetachedFromWindow(viewHolder: RecyclerView.ViewHolder) {
        val delegate = getDelegateForViewType(viewHolder.itemViewType) ?: throw NullPointerException(("No delegate found for "
                + viewHolder
                + " for item at position = "
                + viewHolder.adapterPosition
                + " for viewType = "
                + viewHolder.itemViewType))
        delegate!!.onViewDetachedFromWindow(viewHolder)
    }

    /**
     * Set a fallback delegate that should be used if no [AdapterDelegate] has been found that
     * can handle a certain view type.
     *
     * @param fallbackDelegate The [AdapterDelegate] that should be used as fallback if no
     * other AdapterDelegate has handled a certain view type. `null` you can set this to
     * null if
     * you want to remove a previously set fallback AdapterDelegate
     */
    fun setFallbackDelegate(
            fallbackDelegate: AdapterDelegate<T>?): AdapterDelegatesManager<T> {
        this.defaultFallbackDelegate = fallbackDelegate
        return this
    }

    /**
     * Get the view type integer for the given [AdapterDelegate]
     *
     * @param delegate The delegate we want to know the view type for
     * @return -1 if passed delegate is unknown, otherwise the view type integer
     */
    fun getViewType(delegate: AdapterDelegate<T>): Int {
        if (delegate == null) {
            throw NullPointerException("Delegate is null")
        }

        val index = delegates.indexOfValue(delegate)
        return if (index == -1) {
            -1
        } else delegates.keyAt(index)
    }

    /**
     * Get the [AdapterDelegate] associated with the given view type integer
     *
     * @param viewType The view type integer we want to retrieve the associated
     * delegate for.
     * @return The [AdapterDelegate] associated with the view type param if it exists,
     * the fallback delegate otherwise if it is set or returns `null` if no delegate is
     * associated to this viewType (and no fallback has been set).
     */
    fun getDelegateForViewType(viewType: Int): AdapterDelegate<T>? {
        val delegate = delegates.get(viewType) ?: if (defaultFallbackDelegate == null) {
            return null
        } else {
            return defaultFallbackDelegate
        }

        return delegate
    }

    /**
     * Get the fallback delegate
     *
     * @return The fallback delegate or `null` if no fallback delegate has been set
     * @see .setFallbackDelegate
     */
    fun getFallbackDelegate(): AdapterDelegate<T>? {
        return defaultFallbackDelegate
    }

}