package com.example.miquelcastanys.cleanlearning.presenter

import com.example.domain.entity.MovieListEntity
import com.example.domain.interactor.GetMostPopularMoviesUseCase
import com.example.domain.interactor.GetSearchMoviesUseCase
import com.example.domain.interactor.SearchMoviesUseCase
import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity
import com.example.miquelcastanys.cleanlearning.entities.FooterViewViewEntity
import com.example.miquelcastanys.cleanlearning.entities.mapper.MoviesListPresentationMapper
import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesView
import io.reactivex.observers.DefaultObserver
import javax.inject.Inject

@PerFragment
class MostPopularMoviesPresenter @Inject constructor(private val mostPopularMoviesUseCase: GetMostPopularMoviesUseCase, private val searchMovies: GetSearchMoviesUseCase) {

    @Inject
    lateinit var view: MostPopularMoviesView
    val moviesList: ArrayList<BaseListViewEntity> = ArrayList()
    var isLastPage = false
    var currentPage = 1
    var searchString = ""

    fun start() {
        currentPage = 1
        view.showProgressBar(true)
        if (view.isSearching) {
            searchMovieByText()
        } else {
            getMostPopularMoviesList(true)
        }
    }

    fun getMostPopularMoviesList(refreshList: Boolean = false) {
        mostPopularMoviesUseCase.execute(currentPage++, MoviesListObserver(this, refreshList) as DefaultObserver<Any>)
    }

    fun manageMovieListEntityReceived(refreshList: Boolean, moviesListEntity: MovieListEntity) {
        if (refreshList) moviesList.clear()
        setIsLastPage(currentPage, moviesListEntity.totalPages)
        addResultToMoviesList(MoviesListPresentationMapper.toPresentationObject(moviesListEntity))
    }

    fun hideProgressBar(showProgress: Boolean) = view.showProgressBar(showProgress)

    fun manageList() {
        removeFooter()
        if (!isLastPage) addFooter()
        view.setItems(moviesList)
        view.hideEmptyView()
        view.showRecyclerView()
        view.setLoadingState(false)
        view.showProgressBar(false)
    }

    fun manageEmptyList() {
        currentPage = 1
        moviesList.clear()
        view.showProgressBar(false)
        view.hideRecyclerView()
        view.showEmptyView()
        view.setLoadingState(false)
    }


    private fun addFooter() {
        moviesList.add(FooterViewViewEntity())
    }

    private fun addResultToMoviesList(moviesListResult: List<BaseListViewEntity>) {
        moviesList.addAll(moviesListResult)
    }

    private fun setIsLastPage(page: Int, totalPages: Int) {
        isLastPage = page == totalPages
    }

    private fun removeFooter() {
        moviesList.removeAll { it is FooterViewViewEntity }
    }

    fun searchMovieByText(newText: String? = "", refreshList: Boolean = false) {
        searchString = newText ?: ""
        if (refreshList) currentPage = 1
        searchMovies.execute(searchString, currentPage++, object : SearchMoviesUseCase.Callback {
            override fun onReceived(moviesListEntity: MovieListEntity) {
                manageMovieListEntityReceived(refreshList, moviesListEntity)
                if (moviesListEntity.moviesList.isNotEmpty()) {
                    manageList()
                } else {
                    manageEmptyList()
                }
            }

            override fun onError() =
                manageEmptyList()

        })
    }

    class MoviesListObserver(private val presenter: MostPopularMoviesPresenter, private val refreshList: Boolean) : DefaultObserver<MovieListEntity>() {
        override fun onComplete() {
            presenter.hideProgressBar(false)
        }

        override fun onNext(movieListEntity: MovieListEntity) {
            presenter.manageMovieListEntityReceived(refreshList, movieListEntity)
            if (movieListEntity.moviesList.isNotEmpty()) {
                presenter.manageList()
            } else {
                presenter.manageEmptyList()
            }
        }

        override fun onError(e: Throwable) =
            presenter.manageEmptyList()

    }

}