package com.example.domain.repository

import com.example.domain.entity.MovieListEntity


interface SearchMoviesRepository {
    suspend fun getSearchMovies(searchText: String, page: Int): MovieListEntity
}