package com.example.domain.interactor

import com.example.domain.entity.MovieListEntity


interface SearchMoviesUseCase {
    interface Callback {
        fun onReceived(moviesListEntity: MovieListEntity)
        fun onError()
    }

    fun execute(searchText: String, page: Int, callback: Callback)
}