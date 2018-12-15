package io.github.aleksandersh.plannerapp.plannerdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.aleksandersh.plannerapp.plannerdb.converter.DateConverter
import io.github.aleksandersh.plannerapp.plannerdb.dao.RecordsDao
import io.github.aleksandersh.plannerapp.plannerdb.entity.RecordEntity

/**
 * Created on 02.12.2018.
 * @author AleksanderSh
 */
@Database(
    entities = [RecordEntity::class],
    exportSchema = true,
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class PlannerDb : RoomDatabase() {

    companion object {

        private const val DB_NAME = "planner_db"

        fun getInstance(context: Context): PlannerDb {
            return Room.databaseBuilder(context, PlannerDb::class.java, DB_NAME)
                .build()
        }
    }

    abstract fun getRecordsDao(): RecordsDao
}