package com.example.domain.interactor

import com.example.domain.entity.MovieEntity

interface MostPopularMoviesUseCase {

    interface Callback {
        fun onReceived(movieList: List<MovieEntity>)
        fun onError()
    }

    fun execute(page: Int, callback: Callback)

}