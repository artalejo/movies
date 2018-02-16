package com.android.movies.dependencyinjection.fragment

import com.android.movies.dependencyinjection.scope.PerFragment
import com.android.movies.ui.base.BaseFragment
import com.android.movies.ui.swipableShows.showDetail.ShowDetailFragment
import com.android.movies.ui.swipableShows.showDetail.ShowDetailFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjector {

    @PerFragment
    @ContributesAndroidInjector()
    abstract fun contributeBaseFragmentInjector(): BaseFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [(ShowDetailFragmentModule::class)])
    abstract fun contributeShowDetailFragmentInjector(): ShowDetailFragment

}