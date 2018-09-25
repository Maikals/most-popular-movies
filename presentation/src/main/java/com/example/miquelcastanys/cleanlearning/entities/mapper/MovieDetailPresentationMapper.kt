package com.example.miquelcastanys.cleanlearning.entities.mapper

import com.example.domain.entity.MovieEntity
import com.example.miquelcastanys.cleanlearning.entities.MovieDetailViewEntity

object MovieDetailPresentationMapper {
    fun convertTo(movieEntity: MovieEntity): MovieDetailViewEntity =
            MovieDetailViewEntity(movieEntity.title, movieEntity.voteAverage, movieEntity.backdropPath)
}