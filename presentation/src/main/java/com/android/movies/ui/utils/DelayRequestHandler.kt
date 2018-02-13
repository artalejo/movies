package com.android.movies.ui.utils

import android.os.Handler

class DelayRequestHandler constructor(private val msDelay: Long,
                                      private val functionToExecute: (String) -> Unit) {

    private var delayHandler = Handler()
    private val runnableToExecute = getRunnableToExecute()
    private var queryToSearch = ""

    fun onNewQueryTyped(query: String) {
        queryToSearch = query
        delayHandler.removeCallbacks(runnableToExecute)
        delayHandler.postDelayed(runnableToExecute, msDelay)
    }

    private fun getRunnableToExecute(): Runnable {
        return Runnable { functionToExecute(queryToSearch) }
    }
}