package com.example.domain.repository

import com.example.domain.entity.MovieListEntity


interface MostPopularMoviesRepository {

    suspend fun getMostPopularMovies(page: Int): MovieListEntity

    suspend fun getSavedMostPopularMovies(): MovieListEntity

}