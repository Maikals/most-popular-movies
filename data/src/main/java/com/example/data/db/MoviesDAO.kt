package com.example.data.db

import com.example.data.entity.GenreRealmEntity
import com.example.data.entity.MovieRealmEntity
import com.example.data.extensions.entityExists
import com.example.data.extensions.getAllEntities
import com.example.data.extensions.saveEntities
import com.example.domain.entity.MovieEntity
import io.realm.RealmList

class MoviesDAO(private val realmManager: RealmManager) {
    fun <T> getMostPopularList(block: (List<MovieRealmEntity>) -> T): T? =
            realmManager.executeTransaction { realm ->
                realm.getAllEntities(MovieRealmEntity::class.java) {
                    block(it)
                }
            }

    fun setMostPopularList(moviesList: List<MovieEntity>) =
            realmManager.executeTransaction { realm ->
                realm.saveEntities(moviesList.asSequence()
                        .map {
                            createMovieRealmEntityFromMovieEntity(it)
                        }.toList())
            }!!

    private fun createMovieRealmEntityFromMovieEntity(movie: MovieEntity): MovieRealmEntity =
            MovieRealmEntity().apply {
                id = movie.id
                video = movie.video
                voteAverage = movie.voteAverage
                title = movie.title
                popularity = movie.popularity
                posterPath = movie.posterPath
                backdropPath = movie.backdropPath
                adult = movie.adult
                overview = movie.overview
                releaseDate = movie.releaseDate
                genreIds = RealmList<GenreRealmEntity>().apply {
                    movie.genreIds.forEach { genreId ->
                        GenreRealmEntity().apply {
                            id = genreId
                        }
                    }
                }
            }

    fun movieExists(movieId: Int): Boolean =
            realmManager.executeTransaction { realm ->
                realm.entityExists(MovieRealmEntity::class.java, "id", movieId)
            }!!
}