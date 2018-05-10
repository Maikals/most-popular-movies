package com.example.data.repository.dataSource

import com.example.data.entity.MovieListDTO
import com.example.data.entity.mapper.MovieListMapper
import com.example.data.net.MostPopularMoviesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MostPopularDataStoreImpl @Inject constructor(private val mostPopularMoviesService: MostPopularMoviesService) : MostPopularMoviesStore {
    override fun getMostPopularMoviesList(page: Int, callback: MostPopularMoviesStore.GetMostPopularMoviesListCallback) {
        val call = mostPopularMoviesService.getMostPopularMoviesList(page)
        call.enqueue(object : Callback<MovieListDTO> {

            override fun onResponse(call: Call<MovieListDTO>?, response: Response<MovieListDTO>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    callback.onMostPopularMovies(MovieListMapper.toDomainObject(response.body()!!))
                }
            }

            override fun onFailure(call: Call<MovieListDTO>?, t: Throwable?) {
                callback.onError()
            }

        })
    }
}