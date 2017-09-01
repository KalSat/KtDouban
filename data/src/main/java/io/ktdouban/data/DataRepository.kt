package io.ktdouban.data

import android.content.Context
import android.util.Log
import com.anupcowkur.reservoir.Reservoir
import com.meizu.fetchmoviekt.utils.NetUtils
import io.ktdouban.data.entities.Address
import io.ktdouban.data.entities.MovieCollectionPage
import io.ktdouban.data.network.DoubanDataSource
import io.ktdouban.data.network.GoogleMapDataSource
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
object DataRepository {

    private val TAG = "DataRepository"

    private lateinit var mContext: Context
    private lateinit var mGoogleMapNetwork: GoogleMapDataSource
    private lateinit var mDoubanNetwork: DoubanDataSource

    fun onCreate(context: Context) {
        mContext = context
        initNetwork()
        Reservoir.init(mContext, (5 * 1024 * 1024).toLong())
    }

    fun onDestroy() {
    }

    private fun initNetwork() {
        val mCacheControlInterceptor = Interceptor { chain ->
            var request = chain.request()
            val networkAvailable = NetUtils.isNetworkAvailable(mContext)
            if (!networkAvailable) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val originalResponse = chain.proceed(request)
            if (networkAvailable) {
                // 有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
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
        val mLogInterceptor = Interceptor { chain ->
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
        val mClient = OkHttpClient.Builder()
//                .addInterceptor(mCacheControlInterceptor)
//                .addNetworkInterceptor(mCacheControlInterceptor)
//                .addInterceptor(mLogInterceptor)
                .cache(Cache(File(mContext.externalCacheDir,
                        "responses"), (10 * 1024 * 1024).toLong()))
                .build()
        mGoogleMapNetwork = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GoogleMapDataSource.BASE_URL)
                .client(mClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleMapDataSource::class.java)
        mDoubanNetwork = Retrofit.Builder()
                .baseUrl(DoubanDataSource.BASE_URL)
                .client(mClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DoubanDataSource::class.java)
    }

    fun getAddress(latitude: Double, longitude: Double): Observable<Address> =
            mGoogleMapNetwork.getAddress(latitude.toString() + "," + longitude, "zh-CN", true)

    internal fun getMoviesInTheater(city: String): Observable<MovieCollectionPage> =
            mDoubanNetwork.getMoviesInTheater(city, DoubanDataSource.API_KEY)

}
