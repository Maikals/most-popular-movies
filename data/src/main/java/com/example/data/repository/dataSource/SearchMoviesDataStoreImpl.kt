package com.example.data.repository.dataSource

import com.example.data.entity.MovieListDTO
import com.example.data.entity.mapper.MovieListMapper
import com.example.data.net.SearchMoviesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class SearchMoviesDataStoreImpl @Inject constructor(private val searchMoviesService: SearchMoviesService) : SearchMoviesDataStore {
    override fun getSearchMoviesList(searchText: String, page: Int, callback: SearchMoviesDataStore.GetSearchMoviesListCallback) {
        val searchMovies = searchMoviesService.getSearchMovies(searchText, page)
        searchMovies.enqueue(object : Callback<MovieListDTO> {
            override fun onFailure(call: Call<MovieListDTO>?, t: Throwable?) {
                callback.onError()
            }

            override fun onResponse(call: Call<MovieListDTO>?, response: Response<MovieListDTO>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    callback.onSearchMovies(MovieListMapper.toDomainObject(response.body()!!))
                } else callback.onError()
            }
        })
    }
}