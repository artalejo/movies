package com.android.movies.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.android.movies.network.ApiConstants
import com.android.movies.ui.Navigator
import com.android.movies.ui.utils.ExceptionUtils
import com.android.movies.ui.utils.adapter.ViewType
import com.evernote.android.state.State
import com.evernote.android.state.StateSaver
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * BaseActivity
 *
 * Default Activity that must be subclassed by the rest of activities. Takes charge of application
 * scope variables and its the root of injection
 *
 */
abstract class BaseActivity: AppCompatActivity(), ErrorBaseView, NavigationBaseView, LoadingBaseView,
        EmptyBaseView, AnkoLogger, HasSupportFragmentInjector {

    private val DEFAULT_FRAGMENT_TAG = "DEFAULT_FRAGMENT_TAG"
    @Inject lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>
    protected val DEFAULT_VISIBLE_POSITION = ApiConstants.DEFAULT_VISIBLE_POSITION
    protected val LIMIT_PER_PAGE = ApiConstants.DEFAULT_LIMIT_PER_PAGE
    @Inject lateinit var navigator: Navigator
    @State var infiniteLoadingFinished = false
    @State var allItems: ArrayList<ViewType> = arrayListOf()

    abstract var layout: Int
    var masterPresenter: BasePresenter? = null
    open fun getBasePresenter(): BasePresenter? = null
    open fun populateWithCachedData(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layout)
        StateSaver.restoreInstanceState(this, savedInstanceState)
        onViewLoaded()
        masterPresenter = getBasePresenter()
    }

    override fun onResume() {
        super.onResume()
        if (allItems.size > 0) populateWithCachedData()
        else masterPresenter?.onResume()
    }

    override fun onPause() {
        super.onPause()
        masterPresenter?.onPause()
    }

    abstract fun onViewLoaded()

    override fun showException(exceptionMessage: String) =
            ExceptionUtils.showException(findViewById(android.R.id.content), exceptionMessage)

    override fun showUnauthorizedException() =
            ExceptionUtils.showUnauthorizedException(context = this,
                    functionToExecute = { } )

    override fun showServerError() =
            ExceptionUtils.showServerError(supportFragmentManager, "Error",
                    onBackPressed = { onBackPressed() },
                    onRefreshPressed = { recreate() } )

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState.let { StateSaver.saveInstanceState(this, outState!!) }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingFragmentInjector
    }

    protected fun addFragment(containerViewId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit()
    }

    protected fun replaceFragment(containerViewId: Int, fragment: Fragment,
                                  addToBackStack: Boolean = false,
                                  fragmentTag: String = DEFAULT_FRAGMENT_TAG) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerViewId, fragment)
        if (addToBackStack) transaction.addToBackStack(fragmentTag)
        transaction.commit()
    }
}
