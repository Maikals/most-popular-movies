package com.example.data.repository

import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.entity.MovieListEntity
import com.example.domain.exceptions.CustomException
import com.example.domain.exceptions.ExceptionType
import com.example.domain.repository.MostPopularMoviesRepository
import javax.inject.Inject


class MostPopularMoviesRepositoryImpl @Inject constructor(private val mostPopularMoviesStore: MostPopularMoviesStore) : MostPopularMoviesRepository {
    @Throws(CustomException::class)
    override fun getMostPopularMovies(page: Int) : MovieListEntity =
            try {
                Thread.sleep(10000)
                mostPopularMoviesStore.getMostPopularMoviesList(page)
            } catch (e: Exception) {
                throw CustomException(e, ExceptionType.UNDEFINED)
            }
}