package com.example.data.repository.dataSource

import com.example.domain.entity.MovieListEntity

interface SearchMoviesDataStore  {
    suspend fun getSearchMoviesList(searchText: String, page: Int): MovieListEntity
}