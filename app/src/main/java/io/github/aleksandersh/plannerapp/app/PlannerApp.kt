package io.github.aleksandersh.plannerapp.app

import android.app.Application
import io.github.aleksandersh.plannerapp.BuildConfig
import timber.log.Timber

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
class PlannerApp : Application() {

    lateinit var dimens: CachedDimens

    override fun onCreate() {
        super.onCreate()
        Dependencies.context = this
        dimens = CachedDimens(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}