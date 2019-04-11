package com.aimatus.popularmoviesreactive

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {

    private val tag = this::class.java.canonicalName
    private val compositeDisposable = CompositeDisposable()
    val selectedMovie = MutableLiveData<String>()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        val disposable = Observable
            .fromCallable { NetworkUtils.getPopularMovies() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { popularMoviesResponse -> Log.i(tag, popularMoviesResponse) }
            .subscribeOn(Schedulers.computation())
            .map { popularMoviesResponse ->
                val popularMovies = Gson().fromJson(popularMoviesResponse, MoviesResponse::class.java)
                popularMovies.results
            }
            .subscribe { popularMoviesResponse ->
                Log.i(tag, popularMoviesResponse?.get(0)?.originalTitle)
                selectedMovie.value = popularMoviesResponse?.get(0)?.originalTitle
            }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}