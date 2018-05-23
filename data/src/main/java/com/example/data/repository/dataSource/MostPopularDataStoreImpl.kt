package com.example.data.repository.dataSource

import com.example.data.entity.mapper.MovieListMapper
import com.example.data.net.MostPopularMoviesService
import com.example.domain.entity.MovieListEntity
import io.reactivex.Observable
import javax.inject.Inject


class MostPopularDataStoreImpl @Inject constructor(private val mostPopularMoviesService: MostPopularMoviesService) : MostPopularMoviesStore {
    override fun getMostPopularMoviesList(page: Int): Observable<MovieListEntity> {
        return mostPopularMoviesService
                .getMostPopularMoviesList(page)
                .map { MovieListMapper.toDomainObject(it) }
    }
}