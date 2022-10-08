package com.ann.movielist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ann.movielist.data.MovieListRepositoryImpl
import com.ann.movielist.domain.AddMovieItemUseCase
import com.ann.movielist.domain.EditMovieItemUseCase
import com.ann.movielist.domain.GetMovieItemUseCase
import com.ann.movielist.domain.MovieItem
import kotlinx.coroutines.launch

class MovieItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieListRepositoryImpl(application)

    private val getMovieListUseCase = GetMovieItemUseCase(repository)
    private val addMovieItemUseCase = AddMovieItemUseCase(repository)
    private val editMovieItemUseCase = EditMovieItemUseCase(repository)

    private val _errorInputTitle = MutableLiveData<Boolean>()
    val errorInputTitle: LiveData<Boolean>
        get() = _errorInputTitle

    private val _movieItem = MutableLiveData<MovieItem>()
    val movieItem: LiveData<MovieItem>
        get() = _movieItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getMovieItem(movieItemId: Int){
        viewModelScope.launch {
            val item = getMovieListUseCase.getMovieItem(movieItemId)
            _movieItem.value = item
        }
    }

    fun addMovieItem(inputTitle: String?){
        val title = parseTitle(inputTitle)
        val fieldsValid = validateInput(title)
        if(fieldsValid){
            viewModelScope.launch {
                val movieItem = MovieItem(title, true)
                addMovieItemUseCase.addMovieItem(movieItem)
                finishWork()
            }
        }
    }

    fun editMovieItem(inputTitle: String?){
        val title = parseTitle(inputTitle)
        val fieldsValid = validateInput(title)
        if(fieldsValid){
            _movieItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(title = title)
                    editMovieItemUseCase.editMovieItem(item)
                    finishWork()
                }
            }
        }
    }

    private fun parseTitle(inputTitle: String?):String{
        return inputTitle?:""
    }

    private fun validateInput(title: String):Boolean{
        var result = true
        if (title.isBlank()){
            _errorInputTitle.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputTitle(){
        _errorInputTitle.value = false
    }

    private fun finishWork(){
        _shouldCloseScreen.value = Unit
    }

}