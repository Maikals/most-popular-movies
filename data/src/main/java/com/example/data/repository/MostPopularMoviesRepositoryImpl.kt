package com.example.data.repository

import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject


class MostPopularMoviesRepositoryImpl @Inject constructor(private val mostPopularMoviesStore: MostPopularMoviesStore) : MostPopularMoviesRepository {
    override fun getMostPopularMovies(page: Int) : Single<MovieListEntity> {
        return mostPopularMoviesStore.getMostPopularMoviesList(page)
    }
}