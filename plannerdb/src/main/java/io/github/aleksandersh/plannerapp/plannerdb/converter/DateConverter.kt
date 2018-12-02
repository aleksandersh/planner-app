package io.github.aleksandersh.plannerapp.plannerdb.converter

import androidx.room.TypeConverter
import java.util.*

/**
 * Created on 02.12.2018.
 * @author AleksanderSh
 */
internal class DateConverter {

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun timestampToDate(timestamp: Long?): Date? {
        return timestamp?.let(::Date)
    }
}