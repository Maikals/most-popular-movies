package com.example.data.repository.dataSource

import com.example.data.entity.MovieListDTO
import com.example.data.entity.mapper.MovieListMapper
import com.example.data.net.MostPopularMoviesService
import com.example.domain.entity.MovieListEntity
import javax.inject.Inject


class MostPopularDataStoreImpl @Inject constructor(private val mostPopularMoviesService: MostPopularMoviesService) : MostPopularMoviesStore {
    override fun getMostPopularMoviesList(page: Int): MovieListEntity =
            MovieListMapper.toDomainObject(mostPopularMoviesService.getMostPopularMoviesList(page).execute().body()
                    ?: MovieListDTO(-1, 0, 0, listOf()))

}