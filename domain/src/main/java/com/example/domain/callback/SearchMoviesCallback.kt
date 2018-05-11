package com.example.domain.callback

import com.example.domain.entity.MovieListEntity

interface SearchMoviesCallback {
    fun onSearchMoviesReceived(moviesList: MovieListEntity)

    fun onError()

}