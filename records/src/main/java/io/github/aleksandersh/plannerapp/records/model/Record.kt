package io.github.aleksandersh.plannerapp.records.model

import java.util.*

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
data class Record(
    val id: Long,
    val creationDate: Date,
    val launchDate: Date,
    val title: String,
    val description: String,
    val repeat: Boolean,
    val cycle: String,
    val cycleStep: Int
)