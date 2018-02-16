package com.android.movies.ui.swipableShows.showDetail

import com.android.movies.dependencyinjection.scope.PerFragment
import com.android.movies.ui.showDetail.adapter.SimilarShowsAdapterDelegate
import com.android.movies.ui.utils.adapter.AdapterDelegate
import com.android.movies.ui.utils.adapter.ViewType
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet

@Module
class ShowDetailFragmentModule {

    @Provides
    internal fun provideShowDetailFragmentView(showDetailFragment: ShowDetailFragment): ShowDetailFragView {
        return showDetailFragment
    }

    @Provides
    @PerFragment
    @ElementsIntoSet
    internal fun provideAdapterDelegates() : MutableSet<AdapterDelegate<List<ViewType>>> {
        val delegates = LinkedHashSet<AdapterDelegate<List<ViewType>>>()
        delegates.add(SimilarShowsAdapterDelegate())
        return delegates
    }
}
