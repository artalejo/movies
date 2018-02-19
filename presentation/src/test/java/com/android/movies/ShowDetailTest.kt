package com.android.movies

import com.android.movies.interactor.SimilarShowsInteractor
import com.android.movies.model.ShowInfo
import com.android.movies.repository.ShowsRepository
import com.android.movies.repository.entities.ShowDataEntity
import com.android.movies.repository.entities.mappers.toShowInfo
import com.android.movies.ui.base.AndroidExceptionHandler
import com.android.movies.ui.base.TestContextProvider
import com.android.movies.ui.showDetail.ShowDetailPresenter
import com.android.movies.ui.showDetail.ShowDetailView
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.coroutines.experimental.AbstractCoroutineContextElement

class ShowDetailTest {

    private lateinit var mockedShowsRepo : ShowsRepository
    private lateinit var mockedShowDetailView : ShowDetailView
    private lateinit var mockedExceptionHandler : AndroidExceptionHandler
    private var testContext : AbstractCoroutineContextElement = TestContextProvider.TestContext
    private lateinit var similarShowsPresenter : ShowDetailPresenter


    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mockedShowsRepo = mock()
        mockedShowDetailView = mock()
        mockedExceptionHandler = mock()
        similarShowsPresenter = setUpPresenter()
    }

    @After
    fun tearDown(){
        similarShowsPresenter.onPause()
        reset(mockedShowsRepo, mockedShowDetailView, mockedExceptionHandler)
    }

    @Test
    fun onResumeShouldDisplaySimilarShowsWhenNoExceptionRaised() {
        // given
        val similarShows = generateSimilarShows()
        val result : Result<List<ShowInfo>, *> = Result.of { similarShows }
        whenever(mockedShowsRepo.getSimilarShows(any())).thenReturn(result)
        // when
        similarShowsPresenter.onResume()
        // then
        verify(mockedShowDetailView).getShowId()
        verify(mockedShowDetailView).showSimilarShows(any())
        verifyZeroInteractions(mockedExceptionHandler)
    }

    @Test
    fun onResumeShouldNotifyErrorWhenResultIsFailure() {
        // given
        val exceptionThrown = Exception()
        val result : Result<List<ShowInfo>, *> = Result.Failure(exceptionThrown)
        whenever(mockedShowsRepo.getSimilarShows(any())).thenReturn(result)
        // when
        similarShowsPresenter.onResume()
        // then
        verify(mockedExceptionHandler).notifyException(mockedShowDetailView, exceptionThrown)
    }

    @Test
    fun onPauseCancelsActiveCoroutine() {
        // given
        val mockedInteractor = mock<SimilarShowsInteractor>()
        val showsPresenter = ShowDetailPresenter(mockedShowDetailView, mockedInteractor)
        // when
        showsPresenter.onPause()
        // then
        verify(mockedInteractor).cancel()
    }

    @Test
    fun onBackClickedCallsOnBackPressed() {
        // given
        val mockedInteractor = mock<SimilarShowsInteractor>()
        val showsPresenter = ShowDetailPresenter(mockedShowDetailView, mockedInteractor)
        // when
        showsPresenter.onBackBtnPressed()
        // then
        verify(mockedShowDetailView).onBackPressed()
    }


    private fun generateSimilarShows(): ArrayList<ShowInfo> {
        val showInfo = ShowDataEntity().toShowInfo()
        val shows = arrayListOf<ShowInfo>()
        shows.add(showInfo)
        return shows
    }

    private fun setUpPresenter(): ShowDetailPresenter {
        var interactor = SimilarShowsInteractor(mockedShowsRepo)
        interactor.androidContext = testContext
        var showsPresenter = ShowDetailPresenter(mockedShowDetailView, interactor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        return showsPresenter
    }
}