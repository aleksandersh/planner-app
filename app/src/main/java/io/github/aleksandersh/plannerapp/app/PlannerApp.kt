package io.github.aleksandersh.plannerapp.app

import android.app.Application
import io.github.aleksandersh.plannerapp.BuildConfig
import timber.log.Timber

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
class PlannerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Dependencies.context = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}