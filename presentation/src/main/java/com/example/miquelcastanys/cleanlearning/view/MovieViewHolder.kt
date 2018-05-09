package com.example.miquelcastanys.cleanlearning.view

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.entities.MovieEntity
import com.example.miquelcastanys.cleanlearning.glideModule.GlideApp
import com.example.miquelcastanys.cleanlearning.util.Constants
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(movieEntity: MovieEntity) {
        view.movieTitle.text = movieEntity.title
        val ratingString = "${view.context.getString(R.string.rating)} ${movieEntity.voteAverage}"
        view.movieRating.text = ratingString
        loadImage(movieEntity.imageUri)
    }

    private fun loadImage(imageUrl: String) {
        GlideApp
                .with(view)
                .load("${Constants.BASE_IMAGE_URL}$imageUrl")
                //.placeholder(R.drawable.ic_placeholder_white)
                .centerCrop()
                .into(view.movieImage)
    }
}