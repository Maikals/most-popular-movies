package com.example.data.repository

import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.callback.MostPopularMoviesCallback
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import javax.inject.Inject


class MostPopularMoviesRepositoryImpl @Inject constructor(private val mostPopularMoviesStore: MostPopularMoviesStore) : MostPopularMoviesRepository {
    override fun getMostPopularMovies(page: Int, mostPopularMoviesCallback: MostPopularMoviesCallback) {
        mostPopularMoviesStore.getMostPopularMoviesList(page, object : MostPopularMoviesStore.GetMostPopularMoviesListCallback {
            override fun onMostPopularMovies(moviesList: MovieListEntity) {
                mostPopularMoviesCallback.onMostPopularMoviesListReceived(moviesList)
            }

            override fun onError() {
                mostPopularMoviesCallback.onError()
            }

        })
    }
}