package io.github.aleksandersh.plannerapp.app

import android.annotation.SuppressLint
import android.content.Context
import io.github.aleksandersh.plannerapp.plannerdb.PlannerDb
import io.github.aleksandersh.plannerapp.plannerdb.dao.RecordsDao
import io.github.aleksandersh.plannerapp.records.interactor.RecordsInteractor
import io.github.aleksandersh.plannerapp.records.repository.RecordsRepository
import io.github.aleksandersh.plannerapp.repository.RecordsRepositoryImpl

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
@SuppressLint("StaticFieldLeak")
object Dependencies {

    lateinit var context: Context

    val recordsInteractor: RecordsInteractor by lazy { RecordsInteractor(recordsRepository) }

    private val recordsRepository: RecordsRepository by lazy { RecordsRepositoryImpl(recordsDao) }
    private val recordsDao: RecordsDao by lazy { db.getRecordsDao() }
    private val db: PlannerDb by lazy { PlannerDb.getInstance(context) }
}