package com.example.miquelcastanys.cleanlearning.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.entities.BaseListEntity
import com.example.miquelcastanys.cleanlearning.entities.MovieEntity
import com.example.miquelcastanys.cleanlearning.inflateFromLayout
import com.example.miquelcastanys.cleanlearning.util.Constants
import com.example.miquelcastanys.cleanlearning.view.MovieViewHolder
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.FooterViewHolder

class MostPopularMovieListAdapter(val movieList: List<BaseListEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val FOOTER_TYPE = Constants.FOOTER_TYPE
        private const val MOVIE_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            if (viewType == MOVIE_TYPE)
                MovieViewHolder(parent.inflateFromLayout(R.layout.list_item_movie))
            else FooterViewHolder(parent.inflateFromLayout(R.layout.list_item_footer))


    override fun getItemCount(): Int =
            movieList.size

    override fun getItemViewType(position: Int): Int =
            when (movieList[position]) {
                is MovieEntity -> MOVIE_TYPE
                else -> FOOTER_TYPE
            }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MovieViewHolder)?.bindView(movieList[position] as MovieEntity)
    }

}