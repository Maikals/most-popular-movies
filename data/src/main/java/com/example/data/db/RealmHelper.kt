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


    init {
        Realm.init(context)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .name("moviesRealmDatabase.realm")
                .schemaVersion(3)
//                .encryptionKey(byteArrayOf())
                .build())
    }


    fun getMostPopularList(): CompletableDeferred<List<MovieEntity>> {
        val realmInstance = getRealmInstance()
        val completableDeferred = CompletableDeferred(realmInstance.where(MovieRealmEntity::class.java).findAll().map {
            MovieEntity(it.id!!,
                    it.video!!,
                    it.voteAverage!!,
                    it.title!!,
                    it.popularity!!,
                    it.posterPath!!,
                    it.genreIds?.map { it.id!! } ?: listOf(),
                    it.backdropPath!!,
                    it.adult!!,
                    it.overview!!,
                    it.releaseDate!!)
        })
        realmInstance.close()
        return completableDeferred
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

    fun movieExists(movie: MovieEntity): Boolean {
        val realmInstance = getRealmInstance()

        realmInstance.where(GenreRealmEntity::class.java).notEqualTo("movie.id", 0.toInt())
        val b = realmInstance.where(MovieRealmEntity::class.java).equalTo("id", movie.id).findFirst() != null
        realmInstance.close()
        return b
    }

    fun getGenreEntityByID(genreId: Int): GenreRealmEntity {
        val realmInstance = getRealmInstance()
        val findFirst = realmInstance.where(GenreRealmEntity::class.java).equalTo("id", genreId).findFirst()!!
        realmInstance.close()
        return findFirst
    }

    private fun genreExists(genreId: Int): Boolean =
            getRealmInstance().where(GenreRealmEntity::class.java).equalTo("id", genreId).findFirst() != null

    private fun executeTransaction(fn: (Realm) -> Unit) {
        val realmInstance = getRealmInstance()
        realmInstance.executeTransaction { fn(it) }
        realmInstance.close()
    }

    private fun getRealmInstance() = Realm.getDefaultInstance()
}