package com.example.data.net

import com.example.data.entity.MovieListDTO
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MostPopularMoviesService {
    @GET("movie/popular")
    fun getMostPopularMoviesList(@Query("page") page: Int): Call<MovieListDTO>
}