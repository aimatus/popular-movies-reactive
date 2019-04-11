package com.aimatus.popularmoviesreactive

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val selectedMovie = MutableLiveData<String>()

    init {
        loadPopularMovies()
    }

    private fun loadPopularMovies() {
        val disposable = Observable
            .fromCallable { NetworkUtils.getPopularMovies() }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { popularMoviesResponse -> getPopularMoviesResults(popularMoviesResponse) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { popularMoviesResponse ->
                selectedMovie.value = popularMoviesResponse?.get(0)?.originalTitle
            }
        compositeDisposable.add(disposable)
    }

    private fun getPopularMoviesResults(popularMoviesResponse: String): List<Movie>? {
        val popularMovies = Gson().fromJson(popularMoviesResponse, MoviesResponse::class.java)
        return popularMovies.results
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}