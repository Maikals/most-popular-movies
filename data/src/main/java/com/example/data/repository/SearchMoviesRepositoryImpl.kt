package com.example.data.repository

import com.example.data.repository.dataSource.SearchMoviesDataStore
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.SearchMoviesRepository


class SearchMoviesRepositoryImpl(private val searchMoviesDataStore: SearchMoviesDataStore) : SearchMoviesRepository {
    override suspend fun getSearchMovies(searchText: String, page: Int): MovieListEntity =
            searchMoviesDataStore.getSearchMoviesList(searchText, page)
}