package tokopedia.app.data.repository.moviedetail

import retrofit2.Response
import tokopedia.app.data.entity.Movie
import tokopedia.app.data.entity.MovieDetail

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: String): Response<Movie>
}