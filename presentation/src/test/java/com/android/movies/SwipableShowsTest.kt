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
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.coroutines.experimental.AbstractCoroutineContextElement

class SwipableShowsTest {

    private val LOAD_MORE_PAGE = 1
    private val SHOW_ID = 1L
    private lateinit var mockedShowsRepo: ShowsRepository
    private lateinit var mockedShowsView: SwipableShowsView
    private lateinit var mockedExceptionHandler: AndroidExceptionHandler
    private var testContext : AbstractCoroutineContextElement = TestContextProvider.TestContext
    private lateinit var showsPresenter : SwipableShowsPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mockedShowsRepo = mock()
        mockedShowsView = mock()
        mockedExceptionHandler = mock()
        showsPresenter = setUpPresenter()
    }

    @After
    fun tearDown(){
        showsPresenter.onPause()
        reset(mockedShowsRepo, mockedShowsView, mockedExceptionHandler)
    }

    @Test
    fun onResumeShouldDisplayPopularShowsWhenNoException() {
        // given
        val shows = generateShows()
        val result : Result<List<ShowInfo>, *> = Result.of { shows }
        whenever(mockedShowsRepo.getSimilarShows(any())).thenReturn(result)
        whenever(mockedShowsView.getShowId()).thenReturn(SHOW_ID)
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
        val result : Result<List<ShowInfo>, *> = Result.Failure(exceptionThrown)
        whenever(mockedShowsRepo.getSimilarShows(any())).thenReturn(result)
        whenever(mockedShowsView.getShowId()).thenReturn(SHOW_ID)
        // when
        showsPresenter.loadMoreSimilarShows(LOAD_MORE_PAGE)
        // then
        verify(mockedExceptionHandler).notifyException(mockedShowsView, exceptionThrown)
    }

    private fun generateShows(): ArrayList<ShowInfo> {
        val showInfo = ShowDataEntity().toShowInfo()
        val shows = arrayListOf<ShowInfo>()
        shows.add(showInfo)
        return shows
    }

    private fun setUpPresenter(): SwipableShowsPresenter{
        var interactor = SimilarShowsInteractor(mockedShowsRepo)
        interactor.androidContext = testContext
        var showsPresenter = SwipableShowsPresenter(mockedShowsView, interactor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        return showsPresenter
    }
}