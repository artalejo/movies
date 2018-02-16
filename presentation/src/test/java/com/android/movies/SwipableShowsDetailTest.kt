package com.android.movies

import com.android.movies.ui.swipableShows.showDetail.ShowDetailFragView
import com.android.movies.ui.swipableShows.showDetail.SwipableShowsFragmentPresenter
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.MockitoAnnotations

class SwipableShowsDetailTest {

    private var mockedShowDetailFragView = mock<ShowDetailFragView>()

    companion object {
        @BeforeClass fun setUp() = MockitoAnnotations.initMocks(this)
    }

    @Test
    fun onBackClickedCallsOnBackPressed() {
        // given
        val detailFragPresenter = SwipableShowsFragmentPresenter(mockedShowDetailFragView)
        // when
        detailFragPresenter.onBackBtnPressed()
        // then
        verify(mockedShowDetailFragView).onBackPressed()
    }
}