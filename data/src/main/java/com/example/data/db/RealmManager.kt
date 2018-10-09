package com.example.data.db

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

object RealmManager : KoinComponent {

    private val androidContext: Context by inject()

    private const val REALM_KEY = "8ab5dd83af7c1b20452927c339eb7701e0d727c31682978c7c92a8193b11e595"

    init {
        Realm.init(androidContext)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .name("moviesRealmDatabase.realm")
                .schemaVersion(6)
                .encryptionKey(REALM_KEY.toByteArray())
                .build())
    }

    fun <T> executeTransaction(fn: (Realm) -> T) =
            openRealmInstance { realm ->
                var result: T? = null
                realm.executeTransaction { result = fn(it) }
                result
            }


    fun <T : RealmObject> saveEntities(realm: Realm, entityList: List<T>) {
        entityList.forEach {
            realm.copyToRealmOrUpdate(it)
        }
    }

    fun <T : RealmObject> getEntity(realm: Realm, entityType: Class<T>, idField: String, id: Int): T? =
            realm.where(entityType).equalTo(idField, id).findFirst()

    fun <T : RealmObject, S> getAllEntities(realm: Realm, entityType: Class<T>, block: (List<T>) -> S): S =
            block(realm.where(entityType).findAll().toList())

    fun <T : RealmObject> entityExists(realm: Realm, entityType: Class<T>, idField: String, id: Int): Boolean =
            getEntity(realm, entityType, idField, id) != null

    @Synchronized
    private fun <T> openRealmInstance(fn: (Realm) -> T): T {
        val realm = Realm.getDefaultInstance()
        realm.use {
            return fn(realm)
        }
    }

}