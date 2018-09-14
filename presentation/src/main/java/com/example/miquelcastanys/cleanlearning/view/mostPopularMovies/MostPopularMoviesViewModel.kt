package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.entity.MovieListEntity
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoroutines
import kotlinx.coroutines.experimental.launch

class MostPopularMoviesViewModel(private val useCase: GetMostPopularMoviesUseCaseCoroutines) : ViewModel() {

    var page = 1
    val mostPopularMovies = MutableLiveData<MovieListEntity>()
    val refresh = MutableLiveData<Boolean>()

    fun getMostPopularMovies(refresh: Boolean = false) {
        this.refresh.postValue(refresh)
        if (refresh) page = 1
        launch {
            useCase.coroutinesTest(MostPopularMoviesParams(page++)) {
                mostPopularMovies.postValue(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        useCase.cancel()
    }
}