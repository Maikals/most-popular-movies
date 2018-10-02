package com.example.data.repository.dataSource

import com.example.domain.entity.MovieEntity
import com.example.domain.entity.MovieListEntity


interface MostPopularMoviesStore {
    suspend fun getMostPopularMoviesList(page: Int): MovieListEntity
    fun getMostPopularMoviesLocal(): MovieListEntity
    fun setMostPopularMoviesLocal(moviesList: List<MovieEntity>)
}