package com.example.domain.interactor

import com.example.domain.entity.MovieListEntity

interface MostPopularMoviesUseCase {

    interface Callback {
        fun onReceived(movieList: MovieListEntity)
        fun onError()
    }

    fun execute(page: Int, callback: Callback)

}