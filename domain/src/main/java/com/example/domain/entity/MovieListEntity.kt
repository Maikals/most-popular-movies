package com.example.domain.entity


data class MovieListEntity(val page: Int,
                           val totalPages: Int,
                           val moviesList: List<MovieEntity>)