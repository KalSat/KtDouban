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

package com.meizu.fetchmoviekt.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import android.text.TextUtils

/**
 * Blog : http://blog.csdn.net/u011240877
 * checked
 */
object NetUtils {

    val NETWORK_TYPE_WIFI = "wifi"
    val NETWORK_TYPE_3G = "3g"
    val NETWORK_TYPE_2G = "2g"
    val NETWORK_TYPE_WAP = "wap"
    val NETWORK_TYPE_UNKNOWN = "unknown"
    val NETWORK_TYPE_DISCONNECT = "disconnect"

    fun getNetworkType(context: Context): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo?.type ?: -1
    }

    fun getNetworkTypeName(context: Context): String {
        var type = NETWORK_TYPE_DISCONNECT
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
                ?: return type
        val networkInfo: NetworkInfo = manager.activeNetworkInfo ?: return type

        if (networkInfo.isConnected) {
            val typeName = networkInfo.typeName
            if ("WIFI".equals(typeName, ignoreCase = true)) {
                type = NETWORK_TYPE_WIFI
            } else if ("MOBILE".equals(typeName, ignoreCase = true)) {
                //String proxyHost = android.net.Proxy.getDefaultHost();//deprecated
                val proxyHost = System.getProperty("http.proxyHost")
                type = if (TextUtils.isEmpty(proxyHost)) if (isFastMobileNetwork(context)) NETWORK_TYPE_3G else NETWORK_TYPE_2G else NETWORK_TYPE_WAP
            } else {
                type = NETWORK_TYPE_UNKNOWN
            }
        }
        return type
    }

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        if (info != null && info.isConnected) {
            if (info.state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        try {
            val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (connectivity != null) {
                val info = connectivity.activeNetworkInfo
                return info.isAvailable
            }
        } catch (e: Exception) {
            return false
        }

        return false
    }

    fun isWiFi(cxt: Context): Boolean {
        val cm = cxt.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // wifi的状态：ConnectivityManager.TYPE_WIFI
        // 3G的状态：ConnectivityManager.TYPE_MOBILE
        return cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    //unchecked
    fun openNetSetting(act: Activity) {
        val intent = Intent()
        val cm = ComponentName("com.android.settings", "com.android.settings.WirelessSettings")
        intent.component = cm
        intent.action = "android.intent.action.VIEW"
        act.startActivityForResult(intent, 0)
    }


    /**
     * Whether is fast mobile network
     */

    private fun isFastMobileNetwork(context: Context): Boolean {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        when (telephonyManager.networkType) {
            TelephonyManager.NETWORK_TYPE_1xRTT -> return false
            TelephonyManager.NETWORK_TYPE_CDMA -> return false
            TelephonyManager.NETWORK_TYPE_EDGE -> return false
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> return true
            TelephonyManager.NETWORK_TYPE_EVDO_A -> return true
            TelephonyManager.NETWORK_TYPE_GPRS -> return false
            TelephonyManager.NETWORK_TYPE_HSDPA -> return true
            TelephonyManager.NETWORK_TYPE_HSPA -> return true
            TelephonyManager.NETWORK_TYPE_HSUPA -> return true
            TelephonyManager.NETWORK_TYPE_UMTS -> return true
            TelephonyManager.NETWORK_TYPE_EHRPD -> return true
            TelephonyManager.NETWORK_TYPE_EVDO_B -> return true
            TelephonyManager.NETWORK_TYPE_HSPAP -> return true
            TelephonyManager.NETWORK_TYPE_IDEN -> return false
            TelephonyManager.NETWORK_TYPE_LTE -> return true
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> return false
            else -> return false
        }
    }

    fun setWifiEnabled(context: Context, enabled: Boolean) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = enabled
    }

    fun getWifiScanResults(context: Context): List<ScanResult>? {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return if (wifiManager.startScan()) wifiManager.scanResults else null
    }

    fun getScanResultsByBSSID(context: Context, bssid: String): ScanResult? {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var scanResult: ScanResult? = null
        val f = wifiManager.startScan()
        if (!f) {
            getScanResultsByBSSID(context, bssid)
        }
        val list = wifiManager.scanResults
        if (list != null) {
            for (i in list.indices) {
                scanResult = list[i]
                if (scanResult!!.BSSID == bssid) {
                    break
                }
            }
        }
        return scanResult
    }

    fun getWifiConnectionInfo(context: Context): WifiInfo {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.connectionInfo
    }
}