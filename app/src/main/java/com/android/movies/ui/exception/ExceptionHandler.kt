package com.android.movies.ui.exception

import com.android.movies.ui.base.ErrorBaseView

interface ExceptionHandler {
    fun <T : ErrorBaseView> notifyException(view: T, exception: Exception?)
}