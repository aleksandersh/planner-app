package io.github.aleksandersh.plannerapp.app

import android.annotation.SuppressLint
import android.content.Context
import io.github.aleksandersh.plannerapp.plannerdb.PlannerDb
import io.github.aleksandersh.plannerapp.plannerdb.dao.RecordsDao

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
@SuppressLint("StaticFieldLeak")
object Dependencies {

    var context: Context? = null

    val recordsDao: RecordsDao by lazy { db.getRecordsDao() }
    private val db: PlannerDb by lazy { PlannerDb.getInstance(context!!) }
}