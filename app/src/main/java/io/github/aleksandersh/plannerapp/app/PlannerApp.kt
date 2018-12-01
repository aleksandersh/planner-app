package io.github.aleksandersh.plannerapp.app

import android.app.Application

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
class PlannerApp : Application() {

    lateinit var dimens: CachedDimens

    override fun onCreate() {
        super.onCreate()
        dimens = CachedDimens(this)
    }
}