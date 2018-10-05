package com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui

import android.arch.lifecycle.MutableLiveData
import com.example.domain.entity.MostPopularMoviesParams
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoRoutines
import com.example.miquelcastanys.cleanlearning.view.base.BaseViewModel

class NewActivityDemoViewModel(private val useCase: GetMostPopularMoviesUseCaseCoRoutines) : BaseViewModel() {

    init {
        addUseCases(useCase)
    }

    var requestCounter = MutableLiveData<Int>()
    var currentPage = 1
    val onDataReceived = MutableLiveData<Boolean>()

    fun getMovies() {
        execute(useCase, MostPopularMoviesParams(currentPage++), {
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
}
