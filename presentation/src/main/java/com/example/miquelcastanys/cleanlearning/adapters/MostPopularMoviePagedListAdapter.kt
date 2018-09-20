package com.example.miquelcastanys.cleanlearning.adapters

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity
import com.example.miquelcastanys.cleanlearning.entities.MovieViewEntity
import com.example.miquelcastanys.cleanlearning.inflateFromLayout
import com.example.miquelcastanys.cleanlearning.util.Constants
import com.example.miquelcastanys.cleanlearning.view.MovieViewHolder
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.FooterViewHolder

class MostPopularMoviePagedListAdapter(private val movieList: List<BaseListViewEntity>) : PagedListAdapter<BaseListViewEntity, MovieViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    }

    private var lastPosition: Int = -1


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BaseListViewEntity>() {
            override fun areItemsTheSame(oldItem: BaseListViewEntity?, newItem: BaseListViewEntity?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun areContentsTheSame(oldItem: BaseListViewEntity?, newItem: BaseListViewEntity?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

}