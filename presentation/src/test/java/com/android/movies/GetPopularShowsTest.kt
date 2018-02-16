package com.android.movies

import com.android.movies.interactor.ShowsInteractor
import com.android.movies.model.ShowInfo
import com.android.movies.repository.ShowsRepository
import com.android.movies.repository.entities.ShowDataEntity
import com.android.movies.repository.entities.mappers.toShowInfo
import com.android.movies.ui.base.AndroidExceptionHandler
import com.android.movies.ui.base.TestContextProvider
import com.android.movies.ui.popularShows.PopularShowsPresenter
import com.android.movies.ui.popularShows.PopularShowsView
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.MockitoAnnotations

class GetPopularShowsTest {

    private val LOAD_MORE_PAGE = 1
    private var mockedShowsRepo = mock<ShowsRepository>()
    private var mockedShowsView = mock<PopularShowsView>()
    private var mockedExceptionHandler = mock<AndroidExceptionHandler>()

    companion object {
        @BeforeClass fun setUp() = MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown(){
        reset(mockedShowsRepo, mockedShowsView, mockedExceptionHandler)
    }


    @Test
    fun onResumeShouldDisplayPopularShowsWhenNoException() {
        // given
        val interactor = ShowsInteractor(mockedShowsRepo)
        val shows = generateShows()
        val result : Result<List<ShowInfo>, *> = Result.of { shows }
        interactor.androidContext = TestContextProvider.TestContext
        val showsPresenter = PopularShowsPresenter(mockedShowsView, interactor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        whenever(mockedShowsRepo.getShows(any())).thenReturn(result)
        // when
        showsPresenter.onResume()
        // then
        verify(mockedShowsView).showPopularShows(any())
        verifyZeroInteractions(mockedExceptionHandler)
    }

    @Test
    fun onResumeShouldNotifyErrorWhenResultIsFailure() {
        // given
        val exceptionThrown = Exception()
        val interactor = ShowsInteractor(mockedShowsRepo)
        interactor.androidContext = TestContextProvider.TestContext
        val result : Result<List<ShowInfo>, *> = Result.Failure(exceptionThrown)
        val showsPresenter = PopularShowsPresenter(mockedShowsView, interactor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        whenever(mockedShowsRepo.getShows(any())).thenReturn(result)
        // when
        showsPresenter.onResume()
        // then
        verify(mockedExceptionHandler).notifyException(mockedShowsView, exceptionThrown)
    }

    @Test
    fun onLoadMoreShowsShouldDisplayMoreShowsWhenNoException() {
        // given
        val showsInteractor = ShowsInteractor(mockedShowsRepo)
        val shows = generateShows()
        val result : Result<List<ShowInfo>, *> = Result.of { shows }
        showsInteractor.androidContext = TestContextProvider.TestContext
        val showsPresenter = PopularShowsPresenter(mockedShowsView, showsInteractor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        whenever(mockedShowsRepo.getShows(any())).thenReturn(result)
        // when
        showsPresenter.onLoadMoreShows(LOAD_MORE_PAGE)
        // then
        verify(mockedShowsView).showPopularShows(any())
        verifyZeroInteractions(mockedExceptionHandler)
    }


    private fun generateShows(): ArrayList<ShowInfo> {
        val showInfo = ShowDataEntity().toShowInfo()
        val shows = arrayListOf<ShowInfo>()
        shows.add(showInfo)
        return shows
    }
}