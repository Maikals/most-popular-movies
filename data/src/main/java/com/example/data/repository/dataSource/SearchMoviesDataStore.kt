package com.example.data.repository.dataSource

import com.example.domain.entity.MovieListEntity
import io.reactivex.Single

interface SearchMoviesDataStore {

    fun getSearchMoviesList(searchText: String, page: Int): Single<MovieListEntity>
}