package com.example.data.entity.mapper

import com.example.data.entity.MovieDTO
import com.example.data.entity.MovieListDTO
import com.example.data.entity.MovieRealmEntity
import com.example.domain.entity.MovieEntity
import com.example.domain.entity.MovieListEntity


object MovieListMapper {
    fun toDomainObject(movieListDTO: MovieListDTO?): MovieListEntity =
            if (movieListDTO != null) MovieListEntity(movieListDTO.page ?: 0,
                    movieListDTO.total_pages ?: 0,
                    movieListDTO.results
                            .filter { it.id != null }
                            .map {
                                movieEntityFromMovieDTO(it)
                            }, true)
            else MovieListEntity(-1, -1, listOf(), false)

    private fun movieEntityFromMovieDTO(movieDTO: MovieDTO): MovieEntity {
        return MovieEntity(movieDTO.id ?: 0,
                movieDTO.video ?: false,
                movieDTO.vote_average ?: 0.0,
                movieDTO.title ?: "",
                movieDTO.popularity ?: 0.0,
                movieDTO.poster_path ?: "",
                movieDTO.genre_ids ?: ArrayList(),
                movieDTO.backdrop_path ?: "",
                movieDTO.adult ?: false,
                movieDTO.overview ?: "",
                movieDTO.release_date ?: "")
    }

    fun toDomainObject(movieRealmEntityList: List<MovieRealmEntity>): MovieListEntity =
            MovieListEntity(-1, -1, movieRealmEntityList.map { movie ->
                MovieEntity(movie.id!!,
                        movie.video!!,
                        movie.voteAverage!!,
                        movie.title!!,
                        movie.popularity!!,
                        movie.posterPath!!,
                        movie.genreIds?.map { it.id!! } ?: listOf(),
                        movie.backdropPath!!,
                        movie.adult!!,
                        movie.overview!!,
                        movie.releaseDate!!)
            })
}
