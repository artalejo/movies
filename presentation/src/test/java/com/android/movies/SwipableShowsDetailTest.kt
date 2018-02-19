package com.android.movies

import com.android.movies.ui.swipableShows.showDetail.ShowDetailFragView
import com.android.movies.ui.swipableShows.showDetail.SwipableShowsFragmentPresenter
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class SwipableShowsDetailTest {

    private lateinit var mockedShowDetailFragView : ShowDetailFragView

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mockedShowDetailFragView = mock()
    }

    @After
    fun tearDown(){ reset(mockedShowDetailFragView) }

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