package com.android.movies.ui.error

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.movies.R

class ServerErrorDialogFragment: DialogFragment() {

    var onToolbarBackPressed: (() -> Unit)? = null
    var onToolbarRefreshPressed: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (inflater == null) return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.activity_something_wrong, null, false)
        return view
    }

    private fun onBackPressed() {
        dismiss()
        if (onToolbarBackPressed != null) (onToolbarBackPressed!!)()
        else activity?.let { it.onBackPressed() }
    }
    private fun onRefreshPressed() {
        dismiss()
        if (onToolbarRefreshPressed != null) (onToolbarRefreshPressed!!)()
        else activity?.let { it.onBackPressed() }
    }
}