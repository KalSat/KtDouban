package com.meizu.fetchmoviekt

import android.app.Application
import android.content.Context

/**
 * Created by chengbiao on 17/8/10.
 */
class App : Application() {

    companion object {
        internal lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onTerminate() {
        super.onTerminate()
    }

}