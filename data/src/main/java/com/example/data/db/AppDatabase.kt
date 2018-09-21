package com.example.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.data.entity.GenreIdsRoomEntity
import com.example.data.entity.MovieRoomEntity

@Database(entities = [MovieRoomEntity::class, GenreIdsRoomEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun GenresDao(): GenresDao
}