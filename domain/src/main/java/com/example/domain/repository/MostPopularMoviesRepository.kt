package com.example.domain.repository

import com.example.domain.callback.MostPopularMoviesCallback
import com.example.domain.entity.MovieListEntity
import io.reactivex.Observable


interface MostPopularMoviesRepository {

    fun getMostPopularMovies(page: Int) : Observable<MovieListEntity>

}