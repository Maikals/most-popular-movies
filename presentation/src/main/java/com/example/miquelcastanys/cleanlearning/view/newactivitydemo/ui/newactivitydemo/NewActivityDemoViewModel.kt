package com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.newactivitydemo

import android.arch.lifecycle.MutableLiveData
import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoRoutines
import com.example.miquelcastanys.cleanlearning.view.base.BaseViewModel

class NewActivityDemoViewModel(private val useCase: GetMostPopularMoviesUseCaseCoRoutines) : BaseViewModel() {

    var currentPage = 1
    val onDataReceived = MutableLiveData<Boolean>()

    fun getMovies(){
        execute(useCase, MostPopularMoviesParams(currentPage++), {
            if (it.result) {

            }

            onDataReceived.postValue(it.result)
        }, {
            onDataReceived.postValue(false)
        })
    }

    override fun onCleared() {
        super.onCleared()
        useCase.cancel()
    }
}
