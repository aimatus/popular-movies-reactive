package com.aimatus.popularmoviesreactive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val tag = this::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPopularMovies()
    }

    private fun getPopularMovies() {
        Observable.fromCallable { NetworkUtils.getPopularMovies() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { popularMoviesResponse -> Log.i(tag, popularMoviesResponse) }
            .subscribe()
    }
}
