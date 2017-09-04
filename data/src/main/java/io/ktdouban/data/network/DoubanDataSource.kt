package io.ktdouban.data.network

import io.ktdouban.data.entities.Movie
import io.ktdouban.data.entities.MovieCollectionPage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by chengbiao on 16-3-31.
 */
internal interface DoubanDataSource {

    companion object {
        val BASE_URL = "https://api.douban.com/v2/"
        val API_KEY = "0b2bdeda43b5688921839c8ecb20399b"
    }

    @GET("movie/in_theaters")
    fun getMoviesInTheater(@Query("city") city: String, @Query("apikey") key: String): Observable<MovieCollectionPage>

    @GET("movie/coming_soon")
    fun getMoviesComingSoon(@Query("start") start: Int, @Query("count") count: Int, @Query("apikey") key: String): Observable<MovieCollectionPage>

    @GET("movie/weekly")
    fun getMoviesWeekly(@Query("apikey") key: String): Observable<MovieCollectionPage>

    @GET("movie/new_movies")
    fun getMoviesNew(@Query("apikey") key: String): Observable<MovieCollectionPage>

    @GET("movie/us_box")
    fun getMoviesUs(@Query("apikey") key: String): Observable<MovieCollectionPage>

    @GET("movie/top250")
    fun getMoviesTop250(@Query("start") start: Int, @Query("count") count: Int, @Query("apikey") key: String): Observable<MovieCollectionPage>

    @GET("movie/subject/{id}")
    fun getMovie(@Path("id") id: Int, @Query("apikey") key: String): Observable<Movie>

    @GET("movie/search")
    fun searchMovies(@Query("q") keyword: String, @Query("tag") tag: String, @Query("start") start: Int, @Query("count") count: Int, @Query("apikey") key: String): Observable<MovieCollectionPage>

}