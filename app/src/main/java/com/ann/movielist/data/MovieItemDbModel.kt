package com.ann.movielist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_items")
data class MovieItemDbModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val enabled: Boolean
)