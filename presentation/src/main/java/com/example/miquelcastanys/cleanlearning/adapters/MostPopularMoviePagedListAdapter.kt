package com.example.miquelcastanys.cleanlearning.adapters

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.example.miquelcastanys.cleanlearning.entities.BaseListViewEntity
import com.example.miquelcastanys.cleanlearning.view.MovieViewHolder

class MostPopularMoviePagedListAdapter(private val movieList: List<BaseListViewEntity>) : PagedListAdapter<BaseListViewEntity, MovieViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    }

    private var lastPosition: Int = -1


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BaseListViewEntity>() {
            override fun areItemsTheSame(p0: BaseListViewEntity, p1: BaseListViewEntity): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun areContentsTheSame(p0: BaseListViewEntity, p1: BaseListViewEntity): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

}