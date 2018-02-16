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
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.MockitoAnnotations

class ShowDetailTest {

    private var mockedShowsRepo = mock<ShowsRepository>()
    private var mockedShowDetailView = mock<ShowDetailView>()
    private var mockedExceptionHandler = mock<AndroidExceptionHandler>()

    companion object {
        @BeforeClass fun setUp() = MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown(){
        reset(mockedShowsRepo, mockedShowDetailView, mockedExceptionHandler)
    }

    @Test
    fun onResumeShouldDisplaySimilarShowsWhenNoExceptionRaised() {
        // given
        val interactor = setupInteractor()
        val similarShows = generateSimilarShows()
        val result : Result<List<ShowInfo>, *> = Result.of { similarShows }
        val showsPresenter = ShowDetailPresenter(mockedShowDetailView, interactor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        whenever(mockedShowsRepo.getSimilarShows(any())).thenReturn(result)
        // when
        showsPresenter.onResume()
        // then
        verify(mockedShowDetailView).getShowId()
        verify(mockedShowDetailView).showSimilarShows(any())
        verifyZeroInteractions(mockedExceptionHandler)
    }

    @Test
    fun onResumeShouldNotifyErrorWhenResultIsFailure() {
        // given
        val exceptionThrown = Exception()
        val interactor = setupInteractor()
        val result : Result<List<ShowInfo>, *> = Result.Failure(exceptionThrown)
        val showsPresenter = ShowDetailPresenter(mockedShowDetailView, interactor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        whenever(mockedShowsRepo.getSimilarShows(any())).thenReturn(result)
        // when
        showsPresenter.onResume()
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

    private fun setupInteractor(): SimilarShowsInteractor {
        val interactor = SimilarShowsInteractor(mockedShowsRepo)
        interactor.androidContext = TestContextProvider.TestContext
        return interactor
    }
}