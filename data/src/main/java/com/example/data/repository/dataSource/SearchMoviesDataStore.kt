package com.example.data.repository.dataSource

import com.example.domain.entity.MovieListEntity

interface SearchMoviesDataStore {
    interface GetSearchMoviesListCallback {
        fun onSearchMovies(moviesList: MovieListEntity)
        fun onError()
    }

    fun getSearchMoviesList(searchText: String, page: Int, callback: GetSearchMoviesListCallback)
}