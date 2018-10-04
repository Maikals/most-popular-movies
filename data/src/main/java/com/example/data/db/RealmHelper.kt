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

    fun <T> getMostPopularList(block: (List<MovieRealmEntity>) -> T): T =
            block(getAllEntities(MovieRealmEntity::class.java))

    fun setMostPopularList(moviesList: List<MovieEntity>) =
            saveEntities(moviesList.asSequence()
                    .filter { !movieExists(it.id) }
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
                genreIds = RealmList<GenreRealmEntity>().also { genreRealmList ->
                    movie.genreIds.forEach { genreId -> genreRealmList.add(generateGenreRealmEntity(genreId)) }
                }
            }

    private fun generateGenreRealmEntity(genreId: Int): GenreRealmEntity? =
            if (genreExists(genreId)) getGenreEntityByID(genreId)
            else GenreRealmEntity().apply {
                id = genreId
            }

    fun movieExists(movieId: Int): Boolean =
            entityExists(MovieRealmEntity::class.java, "id", movieId)

    fun getGenreEntityByID(genreId: Int): GenreRealmEntity? =
            getEntity(GenreRealmEntity::class.java, "id", genreId)

    private fun genreExists(genreId: Int): Boolean =
            entityExists(GenreRealmEntity::class.java, "id", genreId)


    private fun executeTransaction(fn: (Realm) -> Unit) =
            openRealmInstance { realm ->
                realm.executeTransaction { fn(it) }
            }

    private fun <T : RealmObject> saveEntities(entityList: List<T>) {
        executeTransaction { realm ->
            entityList.forEach {
                realm.copyToRealmOrUpdate(it)
            }
        }
    }

    private fun <T : RealmObject> getEntity(entityType: Class<T>, idField: String, id: Int): T? =
            openRealmInstance { realm ->
                realm.where(entityType).equalTo(idField, id).findFirst()
            }

    private fun <T : RealmObject> getAllEntities(entityType: Class<T>): List<T> =
            openRealmInstance { realm ->
                realm.where(entityType).findAll()
            }

    private fun <T : RealmObject> entityExists(entityType: Class<T>, idField: String, id: Int): Boolean =
            getEntity(entityType, idField, id) != null

    private fun <T> openRealmInstance(fn: (Realm) -> T): T {
        val realm = Realm.getDefaultInstance()
        val result = fn(realm)
        realm.close()
        return result
    }

}