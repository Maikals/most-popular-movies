package com.example.domain.entity


class MovieListEntity(val page: Int,
                      val totalPages: Int,
                      val moviesList: List<MovieEntity>,
                      var searchedString: String = "",
                      result: Boolean = true) : BaseEntity(result) {
    constructor(page: Int,
                totalPages: Int,
                moviesList: List<MovieEntity>,
                result: Boolean = true)
            : this(page, totalPages, moviesList, "", result)
}