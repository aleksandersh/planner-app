package io.github.aleksandersh.plannerapp.plannerdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.aleksandersh.plannerapp.plannerdb.entity.RecordEntity

/**
 * Created on 02.12.2018.
 * @author AleksanderSh
 */
@Dao
interface RecordsDao {

    @Query("SELECT * FROM records")
    fun selectRecords(): List<RecordEntity>

    @Query("SELECT * FROM records WHERE id = :id")
    fun requireRecordById(id: Long): RecordEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(recordEntity: RecordEntity): Long

    @Query("DELETE FROM records WHERE id = :recordId")
    fun deleteRecordById(recordId: Long)
}