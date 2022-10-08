package com.ann.movielist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ann.movielist.data.MovieListRepositoryImpl
import com.ann.movielist.domain.DeleteMovieItemUseCase
import com.ann.movielist.domain.EditMovieItemUseCase
import com.ann.movielist.domain.GetMovieListUseCase
import com.ann.movielist.domain.MovieItem
import kotlinx.coroutines.launch

class MainViewModel (application: Application): AndroidViewModel(application) {

    private val repository = MovieListRepositoryImpl(application)

    private val getMovieListUseCase = GetMovieListUseCase(repository)
    private val deleteMovieItemUseCase = DeleteMovieItemUseCase(repository)
    private val editMovieItemUseCase = EditMovieItemUseCase(repository)

    val movieList = getMovieListUseCase.getMovieList()


    fun deleteTaskItem(movieItem: MovieItem){
        viewModelScope.launch {
            deleteMovieItemUseCase.deleteMovieItem(movieItem)
        }
    }

    fun changeEnableState(movieItem: MovieItem){
        viewModelScope.launch {
            val newItem = movieItem.copy(enabled = !movieItem.enabled )
            editMovieItemUseCase.editMovieItem(newItem)
        }
    }
}