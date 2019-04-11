package com.aimatus.popularmoviesreactive

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    @Expose
    val results: List<Movie>? = null
)