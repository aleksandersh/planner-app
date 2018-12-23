package io.github.aleksandersh.plannerapp.plannerdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created on 02.12.2018.
 * @author AleksanderSh
 */
@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "creation_date")
    val creationDate: Date,
    @ColumnInfo(name = "next_launch_date")
    val nextLaunchDate: Date,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "repeat")
    val repeat: Boolean,
    @ColumnInfo(name = "cycle")
    val cycle: String,
    @ColumnInfo(name = "cycle_step")
    val cycleStep: Int
)