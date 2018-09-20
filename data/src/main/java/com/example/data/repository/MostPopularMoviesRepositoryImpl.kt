package com.example.data.repository

import android.content.Context
import android.net.ConnectivityManager
import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.MostPopularMoviesRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject


class MostPopularMoviesRepositoryImpl @Inject constructor(private val mostPopularMoviesStore: MostPopularMoviesStore,
                                                          private val context: Context) : MostPopularMoviesRepository {
    override fun getMostPopularMovies(page: Int) : MovieListEntity {
        return if ((context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork != null) {
            val mostPopularMoviesList = mostPopularMoviesStore.getMostPopularMoviesList(page)
            mostPopularMoviesStore.setMostPopularMoviesLocal(mostPopularMoviesList.moviesList)
            mostPopularMoviesList
        } else {
            mostPopularMoviesStore.getMostPopularMoviesLocal()
        }
    }
}