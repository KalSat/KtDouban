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

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

/**
 * Created by chengbiao on 2016/6/15.
 */
internal object LocationHelper {

    fun getLocation(context: Context): Observable<Location> {
        val rxBus: Subject<Location, Location> = SerializedSubject(PublishSubject.create())

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
            rxBus.onError(IllegalStateException("on permissions."))
            return rxBus
        }
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, object : LocationListener {
            override fun onLocationChanged(location: Location) {
                rxBus.onNext(location)
                rxBus.onCompleted()
            }

            override fun onProviderDisabled(provider: String) {
                rxBus.onError(IllegalStateException("provider has bean disabled."))
            }

            override fun onProviderEnabled(provider: String) {

            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

            }
        }, null)

        return rxBus
    }
}
