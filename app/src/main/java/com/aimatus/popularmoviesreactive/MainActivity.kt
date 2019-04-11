package com.aimatus.popularmoviesreactive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tag = this::class.java.canonicalName
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPopularMovies()
    }

    private fun getPopularMovies() {
        val disposable = Observable.fromCallable { NetworkUtils.getPopularMovies() }
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
                mytext.text = popularMoviesResponse?.get(0)?.originalTitle
            }
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose();
    }
}
