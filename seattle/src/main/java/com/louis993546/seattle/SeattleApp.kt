package com.louis993546.seattle

import android.app.Application
import timber.log.Timber

class SeattleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
