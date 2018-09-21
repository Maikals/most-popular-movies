package com.example.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class MovieRoomEntity(@PrimaryKey val id: Int,
                           val video: Boolean,
                           val voteAverage: Double,
                           val title: String,
                           val popularity: Double,
                           val posterPath: String,
//                           val genreIdRoomEntities: List<GenreIdsRoomEntity>,
                           val backdropPath: String,
                           val adult: Boolean,
                           val overview: String,
                           val releaseDate: String)

