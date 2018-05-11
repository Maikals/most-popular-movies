package com.example.domain.repository

import com.example.domain.callback.SearchMoviesCallback


interface SearchMoviesRepository {

    fun getSearchMovies(searchText: String, page: Int, mostPopularMoviesCallback: SearchMoviesCallback)

}