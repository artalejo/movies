package com.android.movies.dependencyinjection

import com.android.movies.dependencyinjection.scope.PerActivity
import com.android.movies.ui.popularShows.PopularShowsActivity
import com.android.movies.ui.popularShows.PopularShowsActivityModule
import com.android.movies.ui.showDetail.ShowDetailActivity
import com.android.movies.ui.showDetail.ShowDetailActivityModule
import com.android.movies.ui.similarShows.SimilarShowsActivity
import com.android.movies.ui.similarShows.SimilarShowsActivityModule
import com.android.movies.ui.splash.SplashActivity
import com.android.movies.ui.swipableShows.SwipableShowsActivity
import com.android.movies.ui.swipableShows.SwipableShowsActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjector {

    @PerActivity
    @ContributesAndroidInjector()
    abstract fun contributeSplashInjector(): SplashActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [(PopularShowsActivityModule::class)])
    abstract fun contributePopularShowsInjector(): PopularShowsActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [(SimilarShowsActivityModule::class)])
    abstract fun contributeSimilarShowsInjector(): SimilarShowsActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [(ShowDetailActivityModule::class)])
    abstract fun contributeShowDetailInjector(): ShowDetailActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [(SwipableShowsActivityModule::class)])
    abstract fun contributeSwipableShowsInjector(): SwipableShowsActivity
}