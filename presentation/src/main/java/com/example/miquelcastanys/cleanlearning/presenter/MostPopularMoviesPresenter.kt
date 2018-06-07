package com.example.miquelcastanys.cleanlearning.presenter

import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.entity.SearchMoviesParams
import com.example.domain.interactor.GetMostPopularMoviesUseCase
import com.example.domain.interactor.GetSearchMoviesUseCase
import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity
import com.example.miquelcastanys.cleanlearning.entities.FooterViewViewEntity
import com.example.miquelcastanys.cleanlearning.entities.mapper.MoviesListPresentationMapper
import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesView
import io.reactivex.observers.DisposableObserver
import java.util.*
import javax.inject.Inject

@PerFragment
class MostPopularMoviesPresenter @Inject constructor(private val mostPopularMoviesUseCase: GetMostPopularMoviesUseCase,
                                                     private val searchMoviesUseCase: GetSearchMoviesUseCase) : Presenter {

    @Inject
    lateinit var view: MostPopularMoviesView
    val moviesList: ArrayList<BaseListViewEntity> = ArrayList()
    var isLastPage = false
    private var currentPage = 1
    private var searchString = ""
    var isSearching = false

    fun start() {
        currentPage = 1
        view.showProgressBar(true)
        if (isSearching) {
            searchMovieByText()
        } else {
            getMostPopularMoviesList(true)
        }
    }

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {
        mostPopularMoviesUseCase.dispose()
        searchMoviesUseCase.dispose()

    }

    fun loadMoreElements() {
        if (isSearching) {
            searchMovieByText(searchString)
        } else {
            getMostPopularMoviesList()
        }
    }

    fun getMostPopularMoviesList(refreshList: Boolean = false) {
        mostPopularMoviesUseCase.execute(MostPopularMoviesParams(currentPage++), MoviesListObserver(this, refreshList))
    }

    fun manageMovieListEntityReceived(refreshList: Boolean, moviesListEntity: MovieListEntity) {
        if (refreshList) moviesList.clear()
        setIsLastPage(currentPage, moviesListEntity.totalPages)
        addResultToMoviesList(MoviesListPresentationMapper.toPresentationObject(moviesListEntity))
    }

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
        isSearching = true
        if (newText != searchString) searchMoviesUseCase.dispose()
        searchString = newText ?: ""
        if (refreshList) currentPage = 1
        searchMoviesUseCase.execute(SearchMoviesParams(currentPage++, searchString), MoviesListObserver(this, refreshList))
    }

    fun cancelSearch() {
        searchMoviesUseCase.dispose()
    }

    class MoviesListObserver(private val presenter: MostPopularMoviesPresenter, private val refreshList: Boolean) : DisposableObserver<MovieListEntity>() {
        override fun onComplete() {
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