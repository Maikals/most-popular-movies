package com.example.data.db

import com.example.data.entity.GenreRealmEntity
import com.example.data.entity.MovieRealmEntity
import com.example.domain.entity.MovieEntity
import io.realm.RealmList

class MoviesDAO(private val realmManager: RealmManager) {
    fun <T> getMostPopularList(block: (List<MovieRealmEntity>) -> T): T? =
            realmManager.executeTransaction { realm ->
                realmManager.getAllEntities(realm, MovieRealmEntity::class.java) {
                    block(it)
                }
            }

    fun setMostPopularList(moviesList: List<MovieEntity>) =
            realmManager.executeTransaction { realm ->
                realmManager.saveEntities(realm, moviesList.asSequence()
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
                realmManager.entityExists(realm, MovieRealmEntity::class.java, "id", movieId)
            }!!
}