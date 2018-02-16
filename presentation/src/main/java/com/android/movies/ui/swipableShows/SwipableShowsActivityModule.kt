package com.android.movies.ui.swipableShows;


import dagger.Module
import dagger.Provides


@Module
class SwipableShowsActivityModule {
    @Provides
    internal fun provideSwipableShowsView(mainActivity: SwipableShowsActivity): SwipableShowsView {
        return mainActivity
    }
}
