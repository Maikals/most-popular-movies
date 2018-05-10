package com.example.miquelcastanys.cleanlearning.presenter

import com.example.domain.entity.MovieEntity
import com.example.domain.interactor.MostPopularMoviesUseCase
import com.example.miquelcastanys.cleanlearning.entities.BaseListEntity
import com.example.miquelcastanys.cleanlearning.entities.FooterEntity
import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesView
import javax.inject.Inject

@PerFragment
class MostPopularMoviesPresenter @Inject constructor(/*private val mostPopularMoviesUseCase: MostPopularMoviesUseCase*/) {

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
        view.setItems(createDummyArray())
        view.showRecyclerView()
        view.showProgressBar(false)
        /*mostPopularMoviesUseCase.execute(currentPage++, object : MostPopularMoviesUseCase.Callback {
            override fun onReceived(moviesList: List<MovieEntity>) {
                view.setItems(createDummyArray())
                view.showProgressBar(false)
            }

            override fun onError() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })*/
    }

    private fun createDummyArray(): List<BaseListEntity> {
        val array = ArrayList<BaseListEntity>()
        array.add(com.example.miquelcastanys.cleanlearning.entities.
                MovieEntity("Guardianes de la Galaxia", "2.5", ""))
        array.add(com.example.miquelcastanys.cleanlearning.entities.
                MovieEntity("Guardianes de la Galaxia", "2.5", ""))
        array.add(com.example.miquelcastanys.cleanlearning.entities.
                MovieEntity("Guardianes de la Galaxia", "2.5", ""))
        array.add(com.example.miquelcastanys.cleanlearning.entities.
                MovieEntity("Guardianes de la Galaxia", "2.5", ""))
        array.add(com.example.miquelcastanys.cleanlearning.entities.
                MovieEntity("Guardianes de la Galaxia", "2.5", ""))

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