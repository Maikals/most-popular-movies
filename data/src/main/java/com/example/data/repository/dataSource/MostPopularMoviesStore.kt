package com.example.data.repository.dataSource

import com.example.domain.entity.MovieListEntity
import io.reactivex.Single


interface MostPopularMoviesStore {
    fun getMostPopularMoviesList(page: Int): MovieListEntity
}