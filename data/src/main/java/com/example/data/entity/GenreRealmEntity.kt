package com.example.data.entity

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class GenreRealmEntity : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    @LinkingObjects("genreIds")
    val movie: RealmResults<MovieRealmEntity>? = null
}
