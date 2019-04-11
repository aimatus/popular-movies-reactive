package com.aimatus.popularmoviesreactive

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("vote_count")
    @Expose
    val voteCount: Long? = null,

    @SerializedName("id")
    @Expose
    val id: Long? = null,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("popularity")
    @Expose
    val popularity: Double? = null,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null,

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,

    @SerializedName("overview")
    @Expose
    val overview: String? = null,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null
)