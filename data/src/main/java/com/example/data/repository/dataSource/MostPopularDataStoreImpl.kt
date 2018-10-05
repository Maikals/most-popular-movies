package com.example.data.repository.dataSource

import com.example.data.db.MoviesDAO
import com.example.data.entity.mapper.MovieListMapper
import com.example.data.net.MostPopularMoviesService
import com.example.domain.entity.MovieEntity
import com.example.domain.entity.MovieListEntity
import javax.inject.Inject


class MostPopularDataStoreImpl @Inject constructor(private val mostPopularMoviesService: MostPopularMoviesService,
                                                   private val moviesDAO: MoviesDAO) : MostPopularMoviesStore {
    override suspend fun getMostPopularMoviesList(page: Int): MovieListEntity =
            MovieListMapper.toDomainObject(mostPopularMoviesService.getMostPopularMoviesList(page).await())

    override fun setMostPopularMoviesLocal(moviesList: List<MovieEntity>) =
            moviesDAO.setMostPopularList(moviesList)


    //In this case, toDomainObject returns a non nullable vale.
    override suspend fun getMostPopularMoviesLocal(): MovieListEntity =
            moviesDAO.getMostPopularList {
                MovieListMapper.toDomainObject(it)
            }!!
}