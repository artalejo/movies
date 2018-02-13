package com.android.movies.ui.base

import android.view.View
import com.android.movies.ui.utils.extensions.setGone
import com.android.movies.ui.utils.extensions.setVisible

interface EmptyBaseView {

    fun showEmptyView(emptyView: View?, infoView: View?) {
        emptyView?.setVisible()
        infoView?.setGone()
    }

    fun hideEmptyView(emptyView: View?, infoView : View?) {
        emptyView?.setGone()
        infoView?.setVisible()
    }
}