package com.example.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = MovieRoomEntity::class,
        parentColumns = ["id"],
        childColumns = ["movieId"])])
data class GenreIdsRoomEntity(@PrimaryKey val genreID: Int, val movieId: Int)