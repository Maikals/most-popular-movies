package com.example.data.db

import com.example.data.entity.GenreRealmEntity
import com.example.data.extensions.entityExists
import com.example.data.extensions.getEntity


class GenreDao(private val realmManager: RealmManager) {

    fun getGenreEntityByID(genreId: Int): GenreRealmEntity? =
            realmManager.executeTransaction { realm ->
                realm.getEntity(GenreRealmEntity::class.java, "id", genreId)
            }


    fun genreExists(genreId: Int): Boolean =
            realmManager.executeTransaction { realm ->
                realm.entityExists(GenreRealmEntity::class.java, "id", genreId)
            }!!
}