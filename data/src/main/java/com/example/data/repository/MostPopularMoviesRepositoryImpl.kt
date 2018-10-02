package com.example.data.repository

import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import javax.inject.Inject


class MostPopularMoviesRepositoryImpl @Inject constructor(private val mostPopularMoviesStore: MostPopularMoviesStore) : MostPopularMoviesRepository {
    override suspend fun getSavedMostPopularMovies(): MovieListEntity =
            mostPopularMoviesStore.getMostPopularMoviesLocal()

    override suspend fun getMostPopularMovies(page: Int): MovieListEntity {
        //delay(10000)
        val mostPopularMoviesList = mostPopularMoviesStore.getMostPopularMoviesList(page)
        mostPopularMoviesStore.setMostPopularMoviesLocal(mostPopularMoviesList.moviesList)
        return mostPopularMoviesList
    }

}