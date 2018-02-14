package com.android.movies.ui.similarShows;


import dagger.Module
import dagger.Provides


@Module
class SimilarShowsActivityModule {

    @Provides
    internal fun provideSimilarShowsView(mainActivity: SimilarShowsActivity): SimilarShowsView {
        return mainActivity
    }
}
