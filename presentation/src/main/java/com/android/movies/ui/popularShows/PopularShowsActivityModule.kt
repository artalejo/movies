package com.android.movies.ui.popularShows;


import com.android.movies.dependencyinjection.scope.PerActivity
import com.android.movies.ui.popularShows.adapter.ShowsAdapterDelegate
import com.android.movies.ui.utils.adapter.AdapterDelegate
import com.android.movies.ui.utils.adapter.LoadingAdapterDelegate
import com.android.movies.ui.utils.adapter.ViewType
import dagger.Module
import dagger.Provides


@Module
class PopularShowsActivityModule {

    @Provides
    internal fun providePopularShowsView(mainActivity: PopularShowsActivity): PopularShowsView {
        return mainActivity
    }

    @Provides
    @PerActivity
    internal fun provideAdapterDelegates()
            : MutableSet<AdapterDelegate<List<ViewType>>> {
        val delegates = LinkedHashSet<AdapterDelegate<List<ViewType>>>()
        delegates.add(ShowsAdapterDelegate())
        delegates.add(LoadingAdapterDelegate())
        return delegates
    }
}
