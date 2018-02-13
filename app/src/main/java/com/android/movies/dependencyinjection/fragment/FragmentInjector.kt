package com.android.movies.dependencyinjection.fragment

import com.android.movies.dependencyinjection.scope.PerFragment
import com.android.movies.ui.base.BaseFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjector {

    @PerFragment
    @ContributesAndroidInjector()
    abstract fun contributeBaseFragmentInjector(): BaseFragment
}