package com.example.domain.interactor

import com.example.domain.callback.SearchMoviesCallback
import com.example.domain.entity.MovieListEntity
import com.example.domain.repository.SearchMoviesRepository
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(private val searchMoviesRepository: SearchMoviesRepository) : SearchMoviesUseCase {
    override fun execute(searchText: String, page: Int, callback: SearchMoviesUseCase.Callback) {
        searchMoviesRepository.getSearchMovies(searchText, page, object : SearchMoviesCallback {
            override fun onError() {
                callback.onError()
            }

            override fun onSearchMoviesReceived(moviesList: MovieListEntity) {
                callback.onReceived(moviesList)
            }

        })
    }

}