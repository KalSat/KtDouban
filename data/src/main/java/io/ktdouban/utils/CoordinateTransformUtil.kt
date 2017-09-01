/*
 *   Copyright (C)  2016 android@19code.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package io.ktdouban.utils

/**
 * https://github.com/wandergis/coordtransform javaScript Version
 */
internal object CoordinateTransformUtil {
    internal var x_pi = 3.14159265358979324 * 3000.0 / 180.0
    internal var pi = 3.1415926535897932384626
    internal var a = 6378245.0
    internal var ee = 0.00669342162296594323

    fun bd09towgs84(lng: Double, lat: Double): DoubleArray {
        val gcj = bd09togcj02(lng, lat)
        val wgs84 = gcj02towgs84(gcj[0], gcj[1])
        return wgs84
    }

    fun wgs84tobd09(lng: Double, lat: Double): DoubleArray {
        val gcj = wgs84togcj02(lng, lat)
        val bd09 = gcj02tobd09(gcj[0], gcj[1])
        return bd09
    }

    fun gcj02tobd09(lng: Double, lat: Double): DoubleArray {
        val z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * x_pi)
        val theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_pi)
        val bd_lng = z * Math.cos(theta) + 0.0065
        val bd_lat = z * Math.sin(theta) + 0.006
        return doubleArrayOf(bd_lng, bd_lat)
    }

    fun bd09togcj02(bd_lon: Double, bd_lat: Double): DoubleArray {
        val x = bd_lon - 0.0065
        val y = bd_lat - 0.006
        val z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi)
        val theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi)
        val gg_lng = z * Math.cos(theta)
        val gg_lat = z * Math.sin(theta)
        return doubleArrayOf(gg_lng, gg_lat)
    }

    fun wgs84togcj02(lng: Double, lat: Double): DoubleArray {
        if (out_of_china(lng, lat)) {
            return doubleArrayOf(lng, lat)
        }
        var dlat = transformlat(lng - 105.0, lat - 35.0)
        var dlng = transformlng(lng - 105.0, lat - 35.0)
        val radlat = lat / 180.0 * pi
        var magic = Math.sin(radlat)
        magic = 1 - ee * magic * magic
        val sqrtmagic = Math.sqrt(magic)
        dlat = dlat * 180.0 / (a * (1 - ee) / (magic * sqrtmagic) * pi)
        dlng = dlng * 180.0 / (a / sqrtmagic * Math.cos(radlat) * pi)
        val mglat = lat + dlat
        val mglng = lng + dlng
        return doubleArrayOf(mglng, mglat)
    }

    fun gcj02towgs84(lng: Double, lat: Double): DoubleArray {
        if (out_of_china(lng, lat)) {
            return doubleArrayOf(lng, lat)
        }
        var dlat = transformlat(lng - 105.0, lat - 35.0)
        var dlng = transformlng(lng - 105.0, lat - 35.0)
        val radlat = lat / 180.0 * pi
        var magic = Math.sin(radlat)
        magic = 1 - ee * magic * magic
        val sqrtmagic = Math.sqrt(magic)
        dlat = dlat * 180.0 / (a * (1 - ee) / (magic * sqrtmagic) * pi)
        dlng = dlng * 180.0 / (a / sqrtmagic * Math.cos(radlat) * pi)
        val mglat = lat + dlat
        val mglng = lng + dlng
        return doubleArrayOf(lng * 2 - mglng, lat * 2 - mglat)
    }

    fun transformlat(lng: Double, lat: Double): Double {
        var ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng))
        ret += (20.0 * Math.sin(6.0 * lng * pi) + 20.0 * Math.sin(2.0 * lng * pi)) * 2.0 / 3.0
        ret += (20.0 * Math.sin(lat * pi) + 40.0 * Math.sin(lat / 3.0 * pi)) * 2.0 / 3.0
        ret += (160.0 * Math.sin(lat / 12.0 * pi) + 320 * Math.sin(lat * pi / 30.0)) * 2.0 / 3.0
        return ret
    }

    fun transformlng(lng: Double, lat: Double): Double {
        var ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng))
        ret += (20.0 * Math.sin(6.0 * lng * pi) + 20.0 * Math.sin(2.0 * lng * pi)) * 2.0 / 3.0
        ret += (20.0 * Math.sin(lng * pi) + 40.0 * Math.sin(lng / 3.0 * pi)) * 2.0 / 3.0
        ret += (150.0 * Math.sin(lng / 12.0 * pi) + 300.0 * Math.sin(lng / 30.0 * pi)) * 2.0 / 3.0
        return ret
    }

    fun out_of_china(lng: Double, lat: Double): Boolean {
        if (lng < 72.004 || lng > 137.8347) {
            return true
        } else if (lat < 0.8293 || lat > 55.8271) {
            return true
        }
        return false
    }
}