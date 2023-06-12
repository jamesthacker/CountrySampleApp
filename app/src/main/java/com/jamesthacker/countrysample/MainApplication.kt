package com.jamesthacker.countrysample

import android.app.Application
import com.jamesthacker.countrysample.data.Environment
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var environment: Environment

    override fun onCreate() {
        super.onCreate()
        if (environment.enableLogging()) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
