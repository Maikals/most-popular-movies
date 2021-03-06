package com.example.data.net

import com.example.data.entity.MovieListDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchMoviesService {
    @GET("search/movie")
    fun getSearchMovies(
            @Query("query") searchText: String,
            @Query("page") page: Int): Single<MovieListDTO>
}