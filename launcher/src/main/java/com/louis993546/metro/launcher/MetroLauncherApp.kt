package com.louis993546.metro.launcher

import android.app.Application
import timber.log.Timber

class MetroLauncherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
