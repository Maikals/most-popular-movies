package com.example.data.db

import android.content.Context
import com.example.data.entity.GenreRealmEntity
import com.example.data.entity.MovieRealmEntity
import com.example.domain.entity.MovieEntity
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.RealmObject
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
                .schemaVersion(6)
                .encryptionKey(REALM_KEY.toByteArray())
                .build())
    }

    fun <T> getMostPopularList(block: (List<MovieRealmEntity>) -> T): T? =
            executeTransaction { realm ->
                getAllEntities(realm, MovieRealmEntity::class.java) {
                    block(it)
                }
            }


    fun setMostPopularList(moviesList: List<MovieEntity>) =
            saveEntities(moviesList.asSequence()
                    .map {
                        createMovieRealmEntityFromMovieEntity(it)
                    }.toList())

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

    fun movieExists(realm: Realm, movieId: Int): Boolean =
            entityExists(realm, MovieRealmEntity::class.java, "id", movieId)

    fun getGenreEntityByID(realm: Realm, genreId: Int): GenreRealmEntity? =
            getEntity(realm, GenreRealmEntity::class.java, "id", genreId)

    private fun genreExists(realm: Realm, genreId: Int): Boolean =
            entityExists(realm, GenreRealmEntity::class.java, "id", genreId)


    private fun <T> executeTransaction(fn: (Realm) -> T) =
            openRealmInstance { realm ->
                var result: T? = null
                realm.executeTransaction { result = fn(it) }
                result
            }


    private fun <T : RealmObject> saveEntities(entityList: List<T>) {
        executeTransaction { realm ->
            entityList.forEach {
                realm.copyToRealmOrUpdate(it)
            }
        }
    }

    private fun <T : RealmObject> getEntity(realm: Realm, entityType: Class<T>, idField: String, id: Int): T? =
            realm.where(entityType).equalTo(idField, id).findFirst()

    private fun <T : RealmObject, S> getAllEntities(realm: Realm, entityType: Class<T>, block: (List<T>) -> S): S =
            block(realm.where(entityType).findAll().toList())

    private fun <T : RealmObject> entityExists(realm: Realm, entityType: Class<T>, idField: String, id: Int): Boolean =
            getEntity(realm, entityType, idField, id) != null

    @Synchronized
    private fun <T> openRealmInstance(fn: (Realm) -> T): T {
        val realm = Realm.getDefaultInstance()
        realm.use {
            return fn(realm)
        }
    }

}