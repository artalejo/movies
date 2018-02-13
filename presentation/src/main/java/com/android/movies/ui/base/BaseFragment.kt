package com.android.movies.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.movies.ui.Navigator
import com.android.movies.ui.utils.ExceptionUtils
import com.evernote.android.state.StateSaver
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * BaseFragment
 *
 * Default Fragment that must be subclassed from all the fragments in app. Takes care of
 * injection and restore elements in State changes.
 *
 * Implements [ErrorBaseView]: used to show Exceptons in app
 * Implements [LoadingBaseView]: Show and hide loading screens
 *
 */
abstract class BaseFragment : Fragment(), ErrorBaseView, LoadingBaseView, AnkoLogger, EmptyBaseView {

    @Inject lateinit var navigator: Navigator
    abstract var layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layout, null)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState.let { StateSaver.saveInstanceState(this, outState!!) }
    }

    override fun showException(exceptionMessage: String) =
            ExceptionUtils.showException(activity.findViewById(android.R.id.content),
                                         exceptionMessage)

    override fun showUnauthorizedException() =
            ExceptionUtils.showUnauthorizedException(context = context,
                    functionToExecute = {} )

    override fun showServerError() =
            ExceptionUtils.showServerError(fragmentManager, "Error",
                    onBackPressed = { activity.onBackPressed() },
                    onRefreshPressed = { activity.recreate() } )
}