package com.android.movies.ui

import android.content.Context
import com.android.movies.ui.popularShows.PopularShowsActivity
import javax.inject.Inject

/**
 * Navigator
 *
 * Class to route between activities
 *
 * @constructor Creates an [Navigator].
 */
class Navigator @Inject constructor() {


    fun navigateToPopularShows(context: Context) =
            context.startActivity(PopularShowsActivity.getIntent(context))
}
