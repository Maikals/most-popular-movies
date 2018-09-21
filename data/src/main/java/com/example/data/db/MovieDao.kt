package com.example.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.data.entity.MovieRoomEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieRoomEntity")
    fun getAll(): List<MovieRoomEntity>

    @Query("SELECT EXISTS(SELECT id FROM MovieRoomEntity WHERE id == :id)")
    fun movieExists(id: Int): Boolean

    @Insert
    fun insertAll(vararg users: MovieRoomEntity)
}