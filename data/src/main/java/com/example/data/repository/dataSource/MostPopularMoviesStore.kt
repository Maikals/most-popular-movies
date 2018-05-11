package com.example.data.repository.dataSource

import com.example.domain.entity.MovieListEntity


interface MostPopularMoviesStore {
    interface GetMostPopularMoviesListCallback {
        fun onMostPopularMovies(moviesList: MovieListEntity)
        fun onError()
    }

    fun getMostPopularMoviesList(page: Int, callback: GetMostPopularMoviesListCallback)
}