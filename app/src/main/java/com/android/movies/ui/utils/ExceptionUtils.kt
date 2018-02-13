package com.android.movies.ui.utils

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.TextView
import com.android.movies.R
import com.android.movies.ui.error.ServerErrorDialogFragment
import org.jetbrains.anko.alert

class ExceptionUtils {

    companion object {
        private const val SERVER_ERROR_DIALOG = "SERVER_ERROR_DIALOG"
        const val ACTIVITY_TITLE = "ACTIVITY_TITLE"

        fun showException(view: View, exceptionMessage: String) {
            val snack = Snackbar.make(view.findViewById(android.R.id.content),
                    exceptionMessage, Snackbar.LENGTH_LONG)
            val view = snack.view
            val tv = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            tv.setTextColor(Color.WHITE)
            snack.show()
        }

        fun showUnauthorizedException(context: Context, functionToExecute: (Unit) -> Unit) {
            val alert = context.alert(R.string.unauthorized_exception_msg) {
                context.getString(R.string.unauthorized_exception_title)
                yesButton { functionToExecute(Unit) }
            }
            alert.cancellable(false)
            alert.show()
        }

        fun showServerError(fragmentManager: FragmentManager, activityTitle: String? = "",
                            onBackPressed: (() -> Unit)? = null,
                            onRefreshPressed: (() -> Unit)? = null) {

            val serverErrorDialogFragment = ServerErrorDialogFragment().apply {
                onToolbarBackPressed = onBackPressed
                onToolbarRefreshPressed = onRefreshPressed
            }
            var extras = Bundle()
            extras.putString(ACTIVITY_TITLE, activityTitle)
            serverErrorDialogFragment.arguments = extras
            serverErrorDialogFragment.setStyle(DialogFragment.STYLE_NORMAL,
                                               R.style.FullScreenDialogFragmentTheme)
            serverErrorDialogFragment.show(fragmentManager, SERVER_ERROR_DIALOG)
        }
    }
}