package com.example.data.repository.dataSource

import com.example.data.entity.mapper.MovieListMapper
import com.example.data.net.SearchMoviesService
import com.example.domain.entity.MovieListEntity
import io.reactivex.Single
import javax.inject.Inject


class SearchMoviesDataStoreImpl @Inject constructor(private val searchMoviesService: SearchMoviesService) : SearchMoviesDataStore {
    override fun getSearchMoviesList(searchText: String, page: Int): Single<MovieListEntity> =
        searchMoviesService
                .getSearchMovies(searchText, page)
                .map { MovieListMapper.toDomainObject(it) }

}