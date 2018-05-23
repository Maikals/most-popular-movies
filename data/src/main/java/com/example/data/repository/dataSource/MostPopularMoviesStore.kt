package com.example.data.repository.dataSource

import com.example.domain.entity.MovieListEntity
import io.reactivex.Observable


interface MostPopularMoviesStore {
    fun getMostPopularMoviesList(page: Int) : Observable<MovieListEntity>
}