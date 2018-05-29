package com.example.domain.repository

import com.example.domain.entity.MovieListEntity
import io.reactivex.Single


interface SearchMoviesRepository {

    fun getSearchMovies(searchText: String, page: Int): Single<MovieListEntity>

}