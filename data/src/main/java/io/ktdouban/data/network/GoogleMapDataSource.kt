package io.ktdouban.data.network

import io.ktdouban.data.entities.Address
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by chengbiao on 16-3-31.
 */
internal interface GoogleMapDataSource {

    companion object {
        val BASE_URL = "http://maps.googleapis.com/maps/api/geocode/"
    }

    @GET("json")
    fun getAddress(@Query("latlng") latLng: String, @Query("language") language: String,
                   @Query("sensor") sensor: Boolean): Observable<Address>

}
