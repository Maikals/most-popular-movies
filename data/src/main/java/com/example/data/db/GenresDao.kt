package com.example.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.example.data.entity.GenreIdsRoomEntity

@Dao
interface GenresDao {

    @Insert
    fun insertAll(vararg genreIdRoomEntities: GenreIdsRoomEntity)
}