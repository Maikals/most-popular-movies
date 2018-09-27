package com.example.domain.entity


class MovieListEntity(val page: Int,
                           val totalPages: Int,
                           val moviesList: List<MovieEntity>, result: Boolean = true) : BaseEntity(result)