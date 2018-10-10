package com.example.miquelcastanys.cleanlearning.view.newactivitydemo

import android.arch.lifecycle.MutableLiveData
import com.example.domain.entity.EmptyParams
import com.example.domain.entity.MostPopularMoviesParams
import com.example.miquelcastanys.cleanlearning.entities.MovieViewEntity
import com.example.miquelcastanys.cleanlearning.entities.mapper.MoviesListPresentationMapper
import com.example.miquelcastanys.cleanlearning.view.base.BaseViewModel

class NewActivityDemoViewModel(private var useCaseWrapper: NewActivityUseCaseWrapper) : BaseViewModel(useCaseWrapper) {

    val movie = MutableLiveData<MovieViewEntity>()
    var requestCounter = MutableLiveData<Int>()
    private var currentPage = 1
    val onDataReceived = MutableLiveData<Boolean>()

    fun getMovies() {
        execute(useCaseWrapper.getMostPopularMoviesUseCase, MostPopularMoviesParams(currentPage++), {
            if (it.result) {
                if (requestCounter.value == null) requestCounter.value = 0
                requestCounter.value = requestCounter.value?.plus(1)
                requestCounter.postValue(requestCounter.value)
            }

            onDataReceived.postValue(it.result)
        }, {
            onDataReceived.postValue(false)
        })
    }

    fun getMovieFromDatabase() {
        execute(useCaseWrapper.getSavedMoviesUseCase, EmptyParams(), {
            if (it.moviesList.isNotEmpty())
                movie.postValue(MoviesListPresentationMapper.toPresentationObject(it)[0] as MovieViewEntity)
        }, {
            onDataReceived.postValue(false)
        })
    }
}
