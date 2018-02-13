package com.android.movies.ui.base

import android.view.View
import com.android.movies.ui.utils.extensions.setGone
import com.android.movies.ui.utils.extensions.setInvisible
import com.android.movies.ui.utils.extensions.setVisible

interface LoadingBaseView {
    fun onLoading(infoView: View?, progressView : View?) {
        infoView?.setInvisible()
        progressView?.setVisible()
    }
    fun onInfoRetrieved(infoView: View?, progressView : View?) {
        infoView?.setVisible()
        progressView?.setGone()
    }
}