package com.example.data.entity.mapper

import com.example.data.entity.MovieDTO
import com.example.data.entity.MovieListDTO
import com.example.domain.entity.MovieEntity
import com.example.domain.entity.MovieListEntity


object MovieListMapper {
    fun toDomainObject(movieListDTO: MovieListDTO): MovieListEntity =
            MovieListEntity(movieListDTO.page ?: 0,
                    movieListDTO.total_pages ?: 0,
                    movieListDTO.results
                            .filter { it.id != null }
                            .map {
                                movieEntityFromMovieDTO(it)
                            })

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
}
