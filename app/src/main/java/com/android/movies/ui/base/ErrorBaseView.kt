package com.android.movies.ui.base

interface ErrorBaseView {
    fun showException(exceptionMessage: String)
    fun showUnauthorizedException()
    fun showServerError()
}
