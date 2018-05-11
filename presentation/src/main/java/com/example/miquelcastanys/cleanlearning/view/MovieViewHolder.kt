package com.example.miquelcastanys.cleanlearning.view

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.entities.MovieViewEntity
import com.example.miquelcastanys.cleanlearning.glideModule.GlideApp
import com.example.miquelcastanys.cleanlearning.util.Constants
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(movieViewEntity: MovieViewEntity) {
        view.movieTitle.text = movieViewEntity.title
        val ratingString = "${view.context.getString(R.string.rating)} ${movieViewEntity.voteAverage}"
        view.movieRating.text = ratingString
        loadImage(movieViewEntity.imageUri)
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