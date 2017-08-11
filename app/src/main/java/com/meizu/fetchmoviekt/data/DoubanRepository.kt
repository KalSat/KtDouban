package com.meizu.fetchmoviekt.data

import com.meizu.fetchmoviekt.data.entities.MovieList

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by chengbiao on 16-3-31.
 */
interface DoubanRepository {

    companion object {
        val BASE_URL = "https://api.douban.com/v2/"
        val API_KEY = "0b2bdeda43b5688921839c8ecb20399b"
    }

    @GET("movie/in_theaters")
    fun getMoviesInTheater(@Query("city") city: String, @Query("apikey") key: String): Observable<MovieList>

}