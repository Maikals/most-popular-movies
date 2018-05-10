package com.example.domain.repository

import com.example.domain.callback.MostPopularMoviesCallback


interface MostPopularMoviesRepository {

    fun getMostPopularMovies(page: Int, mostPopularMoviesCallback: MostPopularMoviesCallback)

}