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
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.coroutines.experimental.AbstractCoroutineContextElement

class GetPopularShowsTest {

    private val LOAD_MORE_PAGE = 1
    private lateinit var mockedShowsRepo : ShowsRepository
    private lateinit var mockedShowsView : PopularShowsView
    private lateinit var mockedExceptionHandler : AndroidExceptionHandler
    private val testContext : AbstractCoroutineContextElement = TestContextProvider.TestContext
    private lateinit var showsPresenter : PopularShowsPresenter

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
    fun onResumeShouldNotifyErrorWhenResultIsFailure() {
        // given
        val exceptionThrown = Exception()
        val result : Result<List<ShowInfo>, *> = Result.Failure(exceptionThrown)
        whenever(mockedShowsRepo.getShows(any())).thenReturn(result)
        // when
        showsPresenter.onResume()
        // then
        verify(mockedExceptionHandler).notifyException(mockedShowsView, exceptionThrown)
    }

    @Test
    fun onLoadMoreShowsShouldDisplayMoreShowsWhenNoException() {
        // given
        val shows = generateShows()
        val result : Result<List<ShowInfo>, *> = Result.of { shows }
        whenever(mockedShowsRepo.getShows(any())).thenReturn(result)
        // when
        showsPresenter.onLoadMoreShows(LOAD_MORE_PAGE)
        // then
        verify(mockedShowsView).showPopularShows(any())
        verifyZeroInteractions(mockedExceptionHandler)
    }

//    @Test
//    fun onResumeShouldDisplayPopularShowsWhenNoException() {
//        // given
//        val shows = generateShows()
//        val result : Result<List<ShowInfo>, *> = Result.of { shows }
//        whenever(mockedShowsRepo.getShows(any())).thenReturn(result)
//        // when
//        showsPresenter.onResume()
//        // then
//        verify(mockedShowsView).showPopularShows(any())
//        verifyZeroInteractions(mockedExceptionHandler)
//    }

    private fun generateShows(): ArrayList<ShowInfo> {
        val showInfo = ShowDataEntity().toShowInfo()
        val shows = arrayListOf<ShowInfo>()
        shows.add(showInfo)
        return shows
    }

    private fun setUpPresenter(): PopularShowsPresenter{
        var interactor = ShowsInteractor(mockedShowsRepo)
        interactor.androidContext = testContext
        var showsPresenter = PopularShowsPresenter(mockedShowsView, interactor)
        showsPresenter.exceptionHandler = mockedExceptionHandler
        return showsPresenter
    }
}