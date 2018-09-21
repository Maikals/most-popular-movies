package com.example.data.db

import android.arch.persistence.room.Room
import android.content.Context
import com.example.data.entity.MovieRoomEntity
import com.example.domain.entity.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbHelper @Inject constructor(val context: Context) {
    private val db = Room.databaseBuilder(context, AppDatabase::class.java, "mostPopularMoviesDB").build()

    fun getMostPopularMovies(): List<MovieEntity> =
            db.movieDao().getAll().map { buildMovieEntity(it) }

    fun insertMovies(movieListEntity: List<MovieEntity>) {
        db.movieDao().insertAll(*movieListEntity.map { movie ->
            MovieRoomEntity(movie.id, movie.video, movie.voteAverage, movie.title, movie.popularity, movie.posterPath,
                    movie.backdropPath, movie.adult, movie.overview, movie.releaseDate)
        }.toTypedArray())
    }

    private fun buildMovieEntity(it: MovieRoomEntity) =
            MovieEntity(it.id, it.video, it.voteAverage, it.title, it.popularity, it.posterPath, listOf(), it.backdropPath, it.adult, it.overview, it.releaseDate)
}