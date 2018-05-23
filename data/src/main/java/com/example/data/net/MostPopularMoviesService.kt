package com.example.data.net

import com.example.data.entity.MovieListDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface MostPopularMoviesService {
    @GET("movie/popular")
    fun getMostPopularMoviesList(@Query("page") page: Int): Observable<MovieListDTO>
}