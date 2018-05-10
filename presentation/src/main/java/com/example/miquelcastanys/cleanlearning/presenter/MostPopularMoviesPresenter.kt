package com.example.miquelcastanys.cleanlearning.presenter

import android.util.Log
import com.example.domain.entity.MovieListEntity
import com.example.domain.interactor.GetMostPopularMoviesUseCase
import com.example.domain.interactor.MostPopularMoviesUseCase
import com.example.miquelcastanys.cleanlearning.entities.BaseListEntity
import com.example.miquelcastanys.cleanlearning.entities.FooterEntity
import com.example.miquelcastanys.cleanlearning.entities.mapper.MoviesListPresentationMapper
import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesView
import javax.inject.Inject

@PerFragment
class MostPopularMoviesPresenter @Inject constructor(private val mostPopularMoviesUseCase: GetMostPopularMoviesUseCase) {

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
            override fun onReceived(moviesList: MovieListEntity) {

                view.setItems(MoviesListPresentationMapper.toPresentationObject(moviesList))
                view.showProgressBar(false)
                view.showRecyclerView()

            }

            override fun onError() {

            }

        })
    }

    private fun createDummyArray(): List<BaseListEntity> {
        val array = ArrayList<BaseListEntity>()
        array.add(com.example.miquelcastanys.cleanlearning.entities.MovieEntity("Guardianes de la Galaxia", 2.5, ""))
        array.add(com.example.miquelcastanys.cleanlearning.entities.MovieEntity("Guardianes de la Galaxia", 2.5, ""))
        array.add(com.example.miquelcastanys.cleanlearning.entities.MovieEntity("Guardianes de la Galaxia", 2.5, ""))
        array.add(com.example.miquelcastanys.cleanlearning.entities.MovieEntity("Guardianes de la Galaxia", 2.5, ""))
        array.add(com.example.miquelcastanys.cleanlearning.entities.MovieEntity("Guardianes de la Galaxia", 2.5, ""))

        return array
    }

    private fun addFooter() {
        moviesList.add(FooterEntity())
    }

    private fun addResultToTvShowList(tvShowListResult: ArrayList<BaseListEntity>) {
        moviesList.addAll(tvShowListResult)
    }

    private fun setIsLastPage(page: Int, totalPages: Int) {
        isLastPage = page == totalPages
    }

    private fun removeFooter() {
        moviesList.removeAll { it is FooterEntity }
    }


}