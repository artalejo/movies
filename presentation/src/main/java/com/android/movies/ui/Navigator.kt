package com.android.movies.ui

import android.content.Context
import com.android.movies.ui.base.BaseActivity
import com.android.movies.ui.entities.ShowViewEntity
import com.android.movies.ui.popularShows.PopularShowsActivity
import com.android.movies.ui.showDetail.ShowDetailActivity
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

    fun navigateToShowDetail(activity: BaseActivity, showInfo: ShowViewEntity) {
        activity.startActivity(ShowDetailActivity.getIntent(activity, showInfo))
        activity.overridePendingTransitionEnter()
    }
}
