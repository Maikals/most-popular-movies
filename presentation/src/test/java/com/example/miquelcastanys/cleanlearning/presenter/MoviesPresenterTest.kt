package com.example.miquelcastanys.cleanlearning.presenter

import com.example.domain.entity.MovieEntity
import com.example.domain.entity.MovieListEntity
import com.example.domain.interactor.GetMostPopularMoviesUseCase
import com.example.domain.interactor.GetSearchMoviesUseCase
import com.example.domain.interactor.MostPopularMoviesUseCase
import com.example.domain.interactor.SearchMoviesUseCase
import com.example.miquelcastanys.cleanlearning.UnitTest
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesView
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock

class MoviesPresenterTest : UnitTest() {

    private lateinit var presenter: MostPopularMoviesPresenter
    @Mock
    private lateinit var mostPopularMoviesUseCase: GetMostPopularMoviesUseCase
    @Mock
    private lateinit var searchMoviesUseCase: GetSearchMoviesUseCase
    @Mock
    private lateinit var view: MostPopularMoviesView
    @Mock
    private lateinit var movieListEntity: MovieListEntity


    @Before
    fun setUp() {
        presenter = MostPopularMoviesPresenter(mostPopularMoviesUseCase, searchMoviesUseCase)
        initializeView()
    }

    private fun initializeView() {
        presenter.view = view
    }

    @Test
    fun getMostPopularMoviesOK() {
        setupGetMostPopularMoviesCallbackOK()

        presenter.getMostPopularMoviesList()

        verify(presenter.view).showProgressBar(false)
        verify(presenter.view).hideEmptyView()
        verify(presenter.view).showRecyclerView()
        verify(presenter.view).setItems(presenter.moviesList)
    }

    @Test
    fun getMostPopularMoviesOKEmpty() {
        setupGetMostPopularMoviesCallbackOKEmpty()

        presenter.getMostPopularMoviesList()

        verify(presenter.view).showEmptyView()
        verify(presenter.view).showProgressBar(false)
        verify(presenter.view).hideRecyclerView()
    }

    @Test
    fun getMostPopularMoviesError() {
        setupGetMostPopularMoviesCallbackError()

        presenter.getMostPopularMoviesList()

        verify(presenter.view).showEmptyView()
        verify(presenter.view).showProgressBar(false)
        verify(presenter.view).hideRecyclerView()
    }

    @Test
    fun getSearchMoviesOK() {
        setupGetSearchMoviesCallbackOK()

        presenter.searchMovieByText()

        verify(presenter.view).showProgressBar(false)
        verify(presenter.view).hideEmptyView()
        verify(presenter.view).showRecyclerView()
        verify(presenter.view).setItems(presenter.moviesList)
    }

    @Test
    fun getSearchMoviesOKEmpty() {
        setupGetSearchMoviesCallbackOKEmpty()

        presenter.searchMovieByText()

        verify(presenter.view).showEmptyView()
        verify(presenter.view).showProgressBar(false)
        verify(presenter.view).hideRecyclerView()
    }

    @Test
    fun getSearchMoviesError() {
        setupGetSearchMoviesCallbackError()

        presenter.searchMovieByText()

        verify(presenter.view).showEmptyView()
        verify(presenter.view).showProgressBar(false)
        verify(presenter.view).hideRecyclerView()
    }

    private fun setupGetMostPopularMoviesCallbackError() {
        doAnswer {
            val callback = it.arguments[1] as MostPopularMoviesUseCase.Callback
            callback.onError()
            null
        }.`when`(mostPopularMoviesUseCase).execute(ArgumentMatchers.anyInt(), any())
    }

    private fun setupGetMostPopularMoviesCallbackOK() {
        doAnswer {
            val callback = it.arguments[1] as MostPopularMoviesUseCase.Callback
            callback.onReceived(createMoviesListEntity())
            null
        }.`when`(mostPopularMoviesUseCase).execute(ArgumentMatchers.anyInt(), any())
    }

    private fun setupGetMostPopularMoviesCallbackOKEmpty() {
        doAnswer {
            val callback = it.arguments[1] as MostPopularMoviesUseCase.Callback
            callback.onReceived(movieListEntity)
            null
        }.`when`(mostPopularMoviesUseCase).execute(ArgumentMatchers.anyInt(), any())
    }

    private fun setupGetSearchMoviesCallbackOK() {
        doAnswer {
            val callback = it.arguments[2] as SearchMoviesUseCase.Callback
            callback.onReceived(createMoviesListEntity())
            null
        }.`when`(searchMoviesUseCase).execute(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), any())
    }

    private fun setupGetSearchMoviesCallbackOKEmpty() {
        doAnswer {
            val callback = it.arguments[2] as SearchMoviesUseCase.Callback
            callback.onReceived(movieListEntity)
            null
        }.`when`(searchMoviesUseCase).execute(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), any())
    }


    private fun setupGetSearchMoviesCallbackError() {
        doAnswer {
            val callback = it.arguments[2] as SearchMoviesUseCase.Callback
            callback.onError()
            null
        }.`when`(searchMoviesUseCase).execute(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), any())
    }

    private fun createMoviesListEntity(): MovieListEntity =
            MovieListEntity(1,
                    2,
                    listOf(createMovieEntity()))

    private fun createMovieEntity(): MovieEntity =
            MovieEntity(0,
                    false,
                    0.0,
                    "",
                    0.0,
                    ",",
                    emptyList(),
                    ",",
                    false,
                    "",
                    "")


}