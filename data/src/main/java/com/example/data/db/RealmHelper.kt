package com.example.data.db

import android.content.Context
import com.example.data.entity.MovieRealmEntity
import com.example.domain.entity.MovieEntity
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.experimental.CompletableDeferred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealmHelper @Inject constructor(context: Context) {


    init {
//        Realm.setDefaultConfiguration(RealmConfiguration.Builder().encryptionKey(byteArrayOf()).build())
        Realm.init(context)
    }


    fun getMostPopularList(): CompletableDeferred<List<MovieEntity>> =
            CompletableDeferred(Realm.getDefaultInstance().where(MovieRealmEntity::class.java).findAll().map {
                MovieEntity(it.id!!, it.video!!, it.voteAverage!!, it.title!!, it.popularity!!, it.posterPath!!, it.genreIds!!, it.backdropPath!!, it.adult!!, it.overview!!, it.releaseDate!!)
            })


    fun setMostPopularList(moviesList: List<MovieEntity>) {

        Realm.getDefaultInstance().executeTransaction { realm ->
            moviesList.forEach {
                if (realm.where(MovieRealmEntity::class.java).equalTo("id", it.id).findAll().size == 0) {
                    val movie = MovieRealmEntity()
                    movie.id = it.id
                    movie.video = it.video
                    movie.voteAverage = it.voteAverage
                    movie.title = it.title
                    movie.popularity = it.popularity
                    movie.posterPath = it.posterPath
                    movie.genreIds = RealmList<Int>().apply {
                        it.genreIds.forEach { genreId -> add(genreId) }
                    }
                    movie.backdropPath = it.backdropPath
                    movie.adult = it.adult
                    movie.overview = it.overview
                    movie.releaseDate = it.releaseDate
                    Realm.getDefaultInstance().copyToRealm(movie)
                }
            }
        }
    }
}