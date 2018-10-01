package com.example.data.repository

import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import kotlinx.coroutines.experimental.delay
import javax.inject.Inject


class MostPopularMoviesRepositoryImpl @Inject constructor(private val mostPopularMoviesStore: MostPopularMoviesStore) : MostPopularMoviesRepository {
    override suspend fun getMostPopularMovies(page: Int): MovieListEntity {
        delay(10000)
        return mostPopularMoviesStore.getMostPopularMoviesList(page)
    }

}