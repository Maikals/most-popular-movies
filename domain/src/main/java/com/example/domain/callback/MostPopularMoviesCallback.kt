package com.example.domain.callback

import com.example.domain.entity.MovieListEntity


interface MostPopularMoviesCallback {
    fun onMostPopularMoviesListReceived(moviesList: MovieListEntity)

    fun onError()

}