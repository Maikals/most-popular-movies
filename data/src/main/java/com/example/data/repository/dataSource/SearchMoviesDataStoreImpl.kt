package com.example.data.repository.dataSource

import com.example.data.entity.mapper.MovieListMapper
import com.example.data.net.SearchMoviesServiceAdapter
import com.example.domain.entity.MovieListEntity


class SearchMoviesDataStoreImpl(private val searchMoviesService: SearchMoviesServiceAdapter) : SearchMoviesDataStore {
    override suspend fun getSearchMoviesList(searchText: String, page: Int): MovieListEntity =
            MovieListMapper.toDomainObject(searchMoviesService
                    .createService().getSearchMovies(searchText, page).await())


}