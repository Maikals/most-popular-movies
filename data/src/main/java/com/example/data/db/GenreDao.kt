package com.example.data.db

import com.example.data.entity.GenreRealmEntity


class GenreDao(private val realmManager: RealmManager) {

    fun getGenreEntityByID(genreId: Int): GenreRealmEntity? =
            realmManager.executeTransaction { realm ->
                realmManager.getEntity(realm, GenreRealmEntity::class.java, "id", genreId)
            }


    fun genreExists(genreId: Int): Boolean =
            realmManager.executeTransaction { realm ->
                realmManager.entityExists(realm, GenreRealmEntity::class.java, "id", genreId)
            }!!
}