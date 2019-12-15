package com.example.moviedetail.domain

import tokopedia.app.abstraction.util.state.ResultState
import tokopedia.app.data.entity.Movie
import tokopedia.app.data.entity.Movies
import tokopedia.app.data.repository.movie.MovieRepository
import tokopedia.app.data.repository.moviedetail.MovieDetailRepository

class MovieDetailUseCase (
    private val repository: MovieDetailRepository
) {
    suspend fun get(movieId: String): ResultState<Movie>{

        val response = repository.getMovieDetail(movieId)
        return  if (response.isSuccessful){
            ResultState.Success(response.body()!!)
        } else {
            ResultState.Error(MOVIE_DETAIL_ERROR)
        }
    }

    companion object {
        private const val MOVIE_DETAIL_ERROR = "aduh, get movie nya gagal"
    }
}