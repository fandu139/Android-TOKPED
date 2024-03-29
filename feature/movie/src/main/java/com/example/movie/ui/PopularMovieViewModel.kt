package com.example.movie.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.PopularMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tokopedia.app.abstraction.util.state.ResultState
import tokopedia.app.data.entity.Movies

interface PopularMovieContract {
    fun getPopularMovie()
}

class PopularMovieViewModel(
    private val useCase: PopularMovieUseCase
) : ViewModel(), PopularMovieContract {

    private val _movies = MutableLiveData<Movies>();
    val movies: LiveData<Movies>
    get() = _movies

    private val _error = MutableLiveData<String>();
    val error: LiveData<String>
        get() = _error

    init {
        getPopularMovie()
    }

    override fun getPopularMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = useCase.get()
            withContext(Dispatchers.Main) {
                when (response){
                    is ResultState.Success -> {
                        Log.d("tes", response.data.toString())
                        _movies.value = response.data
                    }
//                    else {
//                        _error.value = response.error
//                    }
                }
            }
        }
    }
}