package io.ktdouban

import android.app.Application
import io.ktdouban.data.DataRepository

/**
 * Created by chengbiao on 17/8/10.
 */
class App : Application() {

    private val dataRepo = DataRepository

    override fun onCreate() {
        super.onCreate()
        dataRepo.onCreate(applicationContext)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onTerminate() {
        super.onTerminate()
        dataRepo.onDestroy()
    }

}