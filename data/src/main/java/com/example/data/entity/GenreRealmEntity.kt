package com.example.data.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GenreRealmEntity : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var movie: RealmList<MovieRealmEntity>? = null
}
