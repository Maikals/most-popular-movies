package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.domain.entity.MovieListEntity
import javax.inject.Inject

class MostPopularMoviesViewModel @Inject constructor(val movies: MutableLiveData<MovieListEntity> = MutableLiveData()) : ViewModel() {



}