package com.android.movies.ui.popularShows;


import dagger.Module
import dagger.Provides


@Module
class PopularShowsActivityModule {

    @Provides
    internal fun providePopularShowsView(mainActivity: PopularShowsActivity): PopularShowsView {
        return mainActivity
    }
}
