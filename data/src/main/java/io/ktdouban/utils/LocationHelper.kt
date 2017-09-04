package io.ktdouban.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Created by chengbiao on 2016/6/15.
 */
internal object LocationHelper {

    fun getLocation(context: Context): Observable<Location> {
        return Observable.create<Location>(ObservableOnSubscribe<Location> { observableEmitter ->
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_LOW
            criteria.isAltitudeRequired = false
            criteria.isBearingRequired = false
            criteria.isCostAllowed = true
            criteria.powerRequirement = Criteria.POWER_LOW

            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                observableEmitter.onError(IllegalStateException("on permissions."))
                return@ObservableOnSubscribe
            }
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    observableEmitter.onNext(location)
                    observableEmitter.onComplete()
                }

                override fun onProviderDisabled(provider: String) {
                    observableEmitter.onError(IllegalStateException("provider has bean disabled."))
                }

                override fun onProviderEnabled(provider: String) {

                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

                }
            }, null)
        })
    }
}
