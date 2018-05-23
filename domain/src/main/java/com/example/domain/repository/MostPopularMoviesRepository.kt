package com.example.domain.repository

import com.example.domain.entity.MovieListEntity
import io.reactivex.Single


interface MostPopularMoviesRepository {

    fun getMostPopularMovies(page: Int): Single<MovieListEntity>

}