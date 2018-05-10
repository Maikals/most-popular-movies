package com.example.miquelcastanys.cleanlearning.presenter

import com.example.domain.entity.MovieListEntity
import com.example.domain.interactor.GetMostPopularMoviesUseCase
import com.example.domain.interactor.GetSearchMoviesUseCase
import com.example.domain.interactor.MostPopularMoviesUseCase
import com.example.domain.interactor.SearchMoviesUseCase
import com.example.miquelcastanys.cleanlearning.entities.BaseListEntity
import com.example.miquelcastanys.cleanlearning.entities.FooterEntity
import com.example.miquelcastanys.cleanlearning.entities.mapper.MoviesListPresentationMapper
import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesView
import javax.inject.Inject

@PerFragment
class MostPopularMoviesPresenter @Inject constructor(private val mostPopularMoviesUseCase: GetMostPopularMoviesUseCase, private val searchMovies: GetSearchMoviesUseCase) {

    @Inject
    lateinit var view: MostPopularMoviesView
    private val moviesList: ArrayList<BaseListEntity> = ArrayList()
    var isLastPage = false
    var currentPage = 1

    fun start() {
        currentPage = 1
        view.showProgressBar(true)
        getMostPopularMoviesList(true)
    }

    fun getMostPopularMoviesList(refreshList: Boolean = false) {

        mostPopularMoviesUseCase.execute(currentPage++, object : MostPopularMoviesUseCase.Callback {
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

    private fun manageMovieListEntityReceived(refreshList: Boolean, moviesListEntity: MovieListEntity) {
        if (refreshList) moviesList.clear()
        setIsLastPage(currentPage, moviesListEntity.totalPages)
        addResultToTvShowList(MoviesListPresentationMapper.toPresentationObject(moviesListEntity))
    }

    private fun manageList() {
        removeFooter()
        if (!isLastPage) addFooter()
        view.setItems(moviesList)
        view.hideEmptyView()
        view.showRecyclerView()
        view.setLoadingState(false)
        view.showProgressBar(false)
    }

    private fun manageEmptyList() {
        currentPage = 1
        view.showProgressBar(false)
        view.hideRecyclerView()
        view.showEmptyView()
        view.setLoadingState(false)
    }


    private fun addFooter() {
        moviesList.add(FooterEntity())
    }

    private fun addResultToTvShowList(tvShowListResult: List<BaseListEntity>) {
        moviesList.addAll(tvShowListResult)
    }

    private fun setIsLastPage(page: Int, totalPages: Int) {
        isLastPage = page == totalPages
    }

    private fun removeFooter() {
        moviesList.removeAll { it is FooterEntity }
    }

    fun searchMovieByText(newText: String?) {
        searchMovies.execute(newText ?: "", currentPage, object : SearchMoviesUseCase.Callback {
            override fun onReceived(moviesListEntity: MovieListEntity) {
                manageMovieListEntityReceived(true, moviesListEntity)
                manageList()
            }

            override fun onError() {
                manageEmptyList()
            }

        })
    }


}