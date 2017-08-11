package io.ktdouban.data

import android.util.Log
import com.meizu.fetchmoviekt.utils.NetUtils
import io.ktdouban.App
import io.ktdouban.data.entities.Address
import io.ktdouban.data.entities.MovieList
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.io.File

/**
 * Created by chengbiao on 15-7-11.
 */
object DataStore {

    val TAG = "DataStore"

    private val cacheControlInterceptor = Interceptor { chain ->
        var request = chain.request()
        val networkAvailable = NetUtils.isNetworkAvailable(App.context)
        if (!networkAvailable) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }
        val originalResponse = chain.proceed(request)
        if (networkAvailable) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            val cacheControl = request.cacheControl().toString()
            originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // 无网络时，设置超时为4周
            originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build()
        }
    }

    private val logInterceptor = Interceptor { chain ->
        val request = chain.request()
        val t1 = System.nanoTime()
        Log.i(TAG, String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()))
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        Log.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()))
        response
    }

    private val client = OkHttpClient.Builder()
//            .addInterceptor(cacheControlInterceptor)
//            .addNetworkInterceptor(cacheControlInterceptor)
//            .addInterceptor(logInterceptor)
            .cache(Cache(File(App.context.externalCacheDir,
                    "responses"), (10 * 1024 * 1024).toLong()))
            .build()

    private val GOOGLE_MAP_API = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GoogleMapRepository.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleMapRepository::class.java)

    private val DOUBAN_API = Retrofit.Builder()
            .baseUrl(DoubanRepository.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DoubanRepository::class.java)

    fun getAddress(latitude: Double, longitude: Double): Observable<Address> {
        return GOOGLE_MAP_API.getAddress(latitude.toString() + "," + longitude, "zh-CN", true)
    }

    fun getMoviesInTheater(city: String): Observable<MovieList> {
        return DOUBAN_API.getMoviesInTheater(city, DoubanRepository.API_KEY)
    }
}
