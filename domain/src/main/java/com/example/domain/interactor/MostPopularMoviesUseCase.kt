package com.example.domain.interactor

import com.example.domain.entity.MovieListEntity
import com.example.domain.executor.PostExecutionThread
import io.reactivex.observers.DefaultObserver

interface MostPopularMoviesUseCase {

    interface Callback {
        fun onReceived(moviesListEntity: MovieListEntity)
        fun onError()
    }

    fun execute(page: Int, observer: DefaultObserver<Any>)
    val postExecutionThread: PostExecutionThread
}