package com.ann.movielist.domain

data class MovieItem (
    val title: String,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}