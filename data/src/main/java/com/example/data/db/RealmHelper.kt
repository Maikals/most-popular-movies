package com.example.data.db

import android.content.Context
import com.example.data.entity.GenreRealmEntity
import com.example.data.entity.MovieRealmEntity
import com.example.domain.entity.MovieEntity
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import kotlinx.coroutines.experimental.CompletableDeferred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealmHelper @Inject constructor(context: Context) {

    companion object {
        private const val REALM_KEY = "8ab5dd83af7c1b20452927c339eb7701e0d727c31682978c7c92a8193b11e595"
    }

    init {
        Realm.init(context)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .name("moviesRealmDatabase.realm")
                .schemaVersion(4)
                .encryptionKey(REALM_KEY.toByteArray())
                .build())
    }


    fun getMostPopularList(): CompletableDeferred<List<MovieEntity>> =
            openRealmInstance { realm ->
                CompletableDeferred(realm
                        .where(MovieRealmEntity::class.java)
                        .findAll()
                        .map { movie ->
                            MovieEntity(movie.id!!,
                                    movie.video!!,
                                    movie.voteAverage!!,
                                    movie.title!!,
                                    movie.popularity!!,
                                    movie.posterPath!!,
                                    movie.genreIds?.map { it.id!! } ?: listOf(),
                                    movie.backdropPath!!,
                                    movie.adult!!,
                                    movie.overview!!,
                                    movie.releaseDate!!)
                        })
            }


    fun setMostPopularList(moviesList: List<MovieEntity>) =
            executeTransaction { realm ->
                moviesList.forEach {
                    if (!movieExists(it))
                        realm.copyToRealmOrUpdate(createMovieRealmEntityFromMovieEntity(it))
                }
            }

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
                genreIds = RealmList<GenreRealmEntity>().also { genreRealmList ->
                    movie.genreIds.forEach { genreId -> genreRealmList.add(generateGenreRealmEntity(genreId)) }
                }
            }

    private fun generateGenreRealmEntity(genreId: Int): GenreRealmEntity? =
            if (genreExists(genreId)) getGenreEntityByID(genreId)
            else GenreRealmEntity().apply {
                id = genreId
            }

    fun movieExists(movie: MovieEntity): Boolean =
            openRealmInstance { realm ->
                realm.where(MovieRealmEntity::class.java).equalTo("id", movie.id).findFirst() != null
            }

    fun getGenreEntityByID(genreId: Int): GenreRealmEntity =
            openRealmInstance { realm ->
                realm.where(GenreRealmEntity::class.java).equalTo("id", genreId).findFirst()!!
            }

    private fun genreExists(genreId: Int): Boolean =
            openRealmInstance { realm ->
                realm.where(GenreRealmEntity::class.java).equalTo("id", genreId).findFirst() != null
            }

    private fun executeTransaction(fn: (Realm) -> Unit) =
            openRealmInstance { realm ->
                realm.executeTransaction { fn(it) }
            }

    private fun <T> openRealmInstance(fn: (Realm) -> T): T {
        val realm = Realm.getDefaultInstance()
        val result = fn(realm)
        realm.close()
        return result
    }

}