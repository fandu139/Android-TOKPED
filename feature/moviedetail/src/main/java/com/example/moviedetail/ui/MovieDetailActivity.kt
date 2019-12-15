package com.example.moviedetail.ui

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.moviedetail.R
import com.example.moviedetail.domain.MovieDetailUseCase
import com.example.moviedetail.factory.MovieDetailFactory
import kotlinx.android.synthetic.main.activity_movie_detail.*
import tokopedia.app.abstraction.base.BaseActivity
import tokopedia.app.abstraction.util.ext.load
import tokopedia.app.data.entity.Movie
import tokopedia.app.data.repository.moviedetail.MovieDetailRepository
import tokopedia.app.data.repository.moviedetail.MovieDetailRepositorylmpl
import tokopedia.app.data.routes.NetworkServices
import tokopedia.app.network.Network

class MovieDetailActivity : BaseActivity() {
    override fun contentView(): Int = R.layout.activity_movie_detail

    private lateinit var repository: MovieDetailRepository
    private lateinit var useCase: MovieDetailUseCase
    private lateinit var viewModel: MovieDetailViewModel

    override fun initObservable() {
        val networkBuilder = Network.builder().create(NetworkServices::class.java)

        // init repository
        repository = MovieDetailRepositorylmpl(networkBuilder)

        // init use case
        useCase = MovieDetailUseCase(repository)

        // init viewModel
        viewModel = ViewModelProviders
            .of(this, MovieDetailFactory(useCase))
            .get(MovieDetailViewModel::class.java)
    }

    override fun initView() {
        intent?.data?.lastPathSegment?.let {
            viewModel.setMovieId(it)
        }

        viewModel.error.observe(this, onShowError())

        viewModel.movie.observe(this, Observer {
//            showToast(it.title)
            showMovieDetail(it)
        })
    }

    private fun showMovieDetail(movie: Movie){
        imgBanner.load(movie.bannerUrl())
        imgPoster.load(movie.posterUrl())
        txtMovieName.text = movie.title
        txtYear.text = movie.releaseDate
        txtContent.text = movie.overview
        txtRating.text = movie.voteAverage.toString()
        txtVote.text = movie.voteCount.toString()

    }

    private fun onShowError(): Observer<String>{
        return Observer {
            showToast(it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}