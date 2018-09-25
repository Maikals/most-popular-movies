package com.example.miquelcastanys.cleanlearning.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity
import com.example.miquelcastanys.cleanlearning.entities.MovieViewEntity
import com.example.miquelcastanys.cleanlearning.inflateFromLayout
import com.example.miquelcastanys.cleanlearning.util.Constants
import com.example.miquelcastanys.cleanlearning.viewHolders.FooterViewHolder
import com.example.miquelcastanys.cleanlearning.viewHolders.MovieViewHolder

class MostPopularMovieListAdapter(private val movieList: List<BaseListViewEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var lastPosition: Int = -1

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
                is MovieViewEntity -> MOVIE_TYPE
                else -> FOOTER_TYPE
            }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder && holder.view != null) {
            holder.bindView(movieList[position] as MovieViewEntity)
            setAnimation(holder.view, position)
        }
    }

    fun restartLastPosition() {
        lastPosition = -1
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_in_right)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

}