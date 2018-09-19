package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoroutines
import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity
import com.example.miquelcastanys.cleanlearning.entities.FooterViewViewEntity
import com.example.miquelcastanys.cleanlearning.entities.mapper.MoviesListPresentationMapper

class MostPopularMoviesViewModel(private val useCase: GetMostPopularMoviesUseCaseCoroutines) : ViewModel() {

    var currentPage = 1
    val mostPopularMovies = MutableLiveData<ArrayList<BaseListViewEntity>>().apply {
        value = ArrayList()
    }
    val onDataReceived = MutableLiveData<Unit>()

    fun getMostPopularMovies(refresh: Boolean = false) {
        if (refresh) currentPage = 1

        useCase.execute(MostPopularMoviesParams(currentPage++)) {
            manageMovieListEntityReceived(refresh, it)
            onDataReceived.postValue(Unit)
        }
    }

    private fun manageMovieListEntityReceived(refreshList: Boolean, moviesListEntity: MovieListEntity) {

        if (refreshList) mostPopularMovies.value?.clear()
        setIsLastPage(currentPage, moviesListEntity.totalPages)
        addResultToMoviesList(MoviesListPresentationMapper.toPresentationObject(moviesListEntity))
        removeFooter()
        if (!isLastPage) mostPopularMovies.value?.add(FooterViewViewEntity())
    }

    private fun addResultToMoviesList(moviesListResult: List<BaseListViewEntity>) {
        mostPopularMovies.value?.addAll(moviesListResult)
    }

    private var isLastPage: Boolean = false

    private fun setIsLastPage(page: Int, totalPages: Int) {
        isLastPage = page == totalPages
    }

    private fun removeFooter() {
        mostPopularMovies.value?.removeAll { it is FooterViewViewEntity }
    }

    override fun onCleared() {
        super.onCleared()
        useCase.cancel()
    }
}