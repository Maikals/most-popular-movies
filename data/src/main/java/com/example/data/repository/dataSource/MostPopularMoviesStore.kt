package com.example.data.repository.dataSource

import com.example.domain.entity.MovieListEntity


interface MostPopularMoviesStore {
    suspend fun getMostPopularMoviesList(page: Int): MovieListEntity
}