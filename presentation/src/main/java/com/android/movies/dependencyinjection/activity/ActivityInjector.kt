package com.android.movies.dependencyinjection

import com.android.movies.dependencyinjection.scope.PerActivity
import com.android.movies.ui.popularShows.PopularShowsActivity
import com.android.movies.ui.popularShows.PopularShowsActivityModule
import com.android.movies.ui.splash.SplashActivity
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
}