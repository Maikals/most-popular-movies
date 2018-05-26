package com.example.miquelcastanys.cleanlearning.presenter

import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieEntity
import com.example.domain.entity.MovieListEntity
import com.example.domain.entity.SearchMoviesParams
import com.example.domain.interactor.GetMostPopularMoviesUseCase
import com.example.domain.interactor.GetSearchMoviesUseCase
import com.example.miquelcastanys.cleanlearning.UnitTest
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesView
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.observers.DisposableObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

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
    @Mock
    private lateinit var params: MostPopularMoviesParams

    @Mock
    private lateinit var searchParams: SearchMoviesParams


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

        presenter.start()
        Mockito.verify(presenter.view).showProgressBar(true)

        presenter.searchMovieByText()

        verify(presenter.view).showEmptyView()
        verify(presenter.view).showProgressBar(false)
        verify(presenter.view).hideRecyclerView()
    }

    private fun setupGetMostPopularMoviesCallbackError() {
        doAnswer {
            val observer = it.arguments[1] as DisposableObserver<MovieListEntity>
            observer.onError(Throwable())
            null
        }.`when`(mostPopularMoviesUseCase).execute(any(), any())
    }

    private fun setupGetMostPopularMoviesCallbackOK() {
        doAnswer {
            val observer = it.arguments[1] as DisposableObserver<MovieListEntity>
            observer.onNext(createMoviesListEntity())
            null
        }.`when`(mostPopularMoviesUseCase).execute(any(), any())
    }

    private fun setupGetMostPopularMoviesCallbackOKEmpty() {
        doAnswer {
            val observer = it.arguments[1] as DisposableObserver<MovieListEntity>
            observer.onNext(movieListEntity)
            null
        }.`when`(mostPopularMoviesUseCase).execute(any(), any())
    }

    private fun setupGetSearchMoviesCallbackOK() {
        doAnswer {
            val observer = it.arguments[1] as DisposableObserver<MovieListEntity>
            observer.onNext(createMoviesListEntity())
            null
        }.`when`(searchMoviesUseCase).execute(any(), any())
    }

    private fun setupGetSearchMoviesCallbackOKEmpty() {
        doAnswer {
            val observer = it.arguments[1] as DisposableObserver<MovieListEntity>
            observer.onNext(movieListEntity)
            null
        }.`when`(searchMoviesUseCase).execute(any(), any())
    }


    private fun setupGetSearchMoviesCallbackError() {
        doAnswer {
            val observer = it.arguments[1] as DisposableObserver<MovieListEntity>
            observer.onError(Throwable())
            null
        }.`when`(searchMoviesUseCase).execute(any(), any())
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