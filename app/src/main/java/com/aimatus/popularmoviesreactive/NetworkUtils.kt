package com.aimatus.popularmoviesreactive

import android.net.Uri
import android.util.Log
import java.io.FileNotFoundException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

// TODO: Replace this with a Retrofit implementation
object NetworkUtils {

    private const val popularMoviesURL = "http://api.themoviedb.org/3/movie/popular"
    private const val apiKey = "api_key"
    private const val invalidUrlErrorMessage = "Invalid URL provided."
    private const val apiKeyValue = BuildConfig.THE_MOVIE_DB_API_KEY

    @Throws(IOException::class)
    fun getPopularMovies(): String? {
        val popularMoviesUrl = getUrl(popularMoviesURL)
        val urlConnection = popularMoviesUrl!!.openConnection() as HttpURLConnection
        return try {
            getResponseString(urlConnection)
        } catch (e: FileNotFoundException) {
            Log.e(NetworkUtils::class.java.simpleName, invalidUrlErrorMessage)
            e.printStackTrace()
            null
        } finally {
            urlConnection.disconnect()
        }
    }

    private fun getUrl(popularMoviesUrl: String): URL? {
        val builtUri = Uri.parse(popularMoviesUrl).buildUpon().appendQueryParameter(apiKey, apiKeyValue).build()
        try {
            return URL(builtUri.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    @Throws(IOException::class)
    private fun getResponseString(urlConnection: HttpURLConnection): String? {
        val `in` = urlConnection.inputStream
        val scanner = Scanner(`in`)
        scanner.useDelimiter("\\A")
        val hasInput = scanner.hasNext()
        return if (hasInput) {
            scanner.next()
        } else {
            null
        }
    }

}