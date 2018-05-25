package com.example.data.repository

import com.example.data.repository.dataSource.SearchMoviesDataStore
import com.example.domain.callback.SearchMoviesCallback
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.SearchMoviesRepository
import io.reactivex.Single
import javax.inject.Inject


class SearchMoviesRepositoryImpl @Inject constructor(private val searchMoviesDataStore: SearchMoviesDataStore) : SearchMoviesRepository {
    override fun getSearchMovies(searchText: String, page: Int): Single<MovieListEntity> =
        searchMoviesDataStore.getSearchMoviesList(searchText, page)
}