package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.arch.lifecycle.MutableLiveData
import com.example.domain.entity.EmptyParams
import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.interactor.GetMostPopularMoviesUseCase
import com.example.domain.interactor.GetSavedMoviesUseCase
import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity
import com.example.miquelcastanys.cleanlearning.entities.FooterViewViewEntity
import com.example.miquelcastanys.cleanlearning.entities.mapper.MoviesListPresentationMapper
import com.example.miquelcastanys.cleanlearning.view.base.BaseViewModel


class MostPopularMoviesViewModel(private val useCaseAllMovies: GetMostPopularMoviesUseCase,
                                 private val localUseCase: GetSavedMoviesUseCase) : BaseViewModel() {

    init {
        addUseCases(useCaseAllMovies, localUseCase)
    }

    var currentPage = 1
    val mostPopularMovies = MutableLiveData<ArrayList<BaseListViewEntity>>().apply {
        value = ArrayList()
    }
    val onDataReceived = MutableLiveData<Boolean>()
    private var loading: Boolean = false


    private var searchString = ""
    var isSearching = false

    fun start() {
        currentPage = 1

        if (isSearching) {
            searchMovieByText()
        } else {
            getMostPopularMovies(true)
        }
    }

    fun searchMovieByText(newText: String? = "", refreshList: Boolean = false) {
        isSearching = true
        if (newText != searchString) {
            /*searchMoviesUseCase.dispose()*/
            searchString = newText ?: ""
        }
        if (refreshList) {
            currentPage = 1
            getMostPopularMovies(searchString)
        }
    }

    @Synchronized
    private fun getMostPopularMovies(refresh: Boolean = false) {
        if (!loading) {
            loading = true
            if (refresh) currentPage = 1
            execute(useCaseAllMovies, MostPopularMoviesParams(currentPage++), {
                if (it.result) {
                    manageMovieListEntityReceived(refresh, it)
                }
                onDataReceived.postValue(it.result)
                loading = false
            }, {
                onDataReceived.postValue(false)
                loading = false
            })
        }
    }

    fun getSavedMovies() {
        execute(localUseCase, EmptyParams(), {
            if (it.result)
                manageMovieListEntityReceived(true, it)
            onDataReceived.postValue(it.result)
        }, {
            onDataReceived.postValue(false)
        })
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

    var isLastPage: Boolean = false

    private fun setIsLastPage(page: Int, totalPages: Int) {
        isLastPage = page == totalPages
    }

    private fun removeFooter() {
        mostPopularMovies.value?.removeAll { it is FooterViewViewEntity }
    }

    fun getMostPopularMovies(searchString: String) {
        //TODO get by search param
    }
}