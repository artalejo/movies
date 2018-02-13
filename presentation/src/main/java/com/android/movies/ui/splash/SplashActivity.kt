package com.android.movies.ui.splash;

import com.android.movies.R
import com.android.movies.ui.base.BaseActivity

class SplashActivity: BaseActivity() {
    override var layout = R.layout.activity_splash
    override fun onViewLoaded() = navigator.navigateToPopularShows(this)
}