package com.android.movies

import com.android.movies.interactor.SimilarShowsInteractor
import com.android.movies.model.ShowInfo
import com.android.movies.repository.ShowsRepository
import com.android.movies.repository.entities.ShowDataEntity
import com.android.movies.repository.entities.mappers.toShowInfo
import com.android.movies.ui.base.AndroidExceptionHandler
import com.android.movies.ui.base.TestContextProvider
import com.android.movies.ui.swipableShows.SwipableShowsPresenter
import com.android.movies.ui.swipableShows.SwipableShowsView
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.MockitoAnnotations

class SwipableShowsTest {

    private val LOAD_MORE_PAGE = 1
    private var mockedShowsRepo = mock<ShowsRepository>()
    private var mockedShowsView = mock<SwipableShowsView>()
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
        val interactor = setupInteractor()
        val shows = generateShows()
        val result : Result<List<ShowInfo>, *> = Result.of { shows }
        val showsPresenter = SwipableShowsPresenter(mockedShowsView, interactor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        whenever(mockedShowsRepo.getSimilarShows(any())).thenReturn(result)
        // when
        showsPresenter.loadMoreSimilarShows(LOAD_MORE_PAGE)
        // then
        verify(mockedShowsView).getShowId()
        verify(mockedShowsView).showSimilarShows(any())
        verifyZeroInteractions(mockedExceptionHandler)
    }

    @Test
    fun onResumeShouldNotifyErrorWhenResultIsFailure() {
        // given
        val exceptionThrown = Exception()
        val interactor = setupInteractor()
        val result : Result<List<ShowInfo>, *> = Result.Failure(exceptionThrown)
        val showsPresenter = SwipableShowsPresenter(mockedShowsView, interactor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        whenever(mockedShowsRepo.getSimilarShows(any())).thenReturn(result)
        // when
        showsPresenter.loadMoreSimilarShows(LOAD_MORE_PAGE)
        // then
        verify(mockedExceptionHandler).notifyException(mockedShowsView, exceptionThrown)
    }

    private fun setupInteractor(): SimilarShowsInteractor {
        val interactor = SimilarShowsInteractor(mockedShowsRepo)
        interactor.androidContext = TestContextProvider.TestContext
        return interactor
    }

    private fun generateShows(): ArrayList<ShowInfo> {
        val showInfo = ShowDataEntity().toShowInfo()
        val shows = arrayListOf<ShowInfo>()
        shows.add(showInfo)
        return shows
    }
}