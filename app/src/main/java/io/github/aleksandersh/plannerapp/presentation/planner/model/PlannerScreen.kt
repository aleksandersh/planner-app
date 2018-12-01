package io.github.aleksandersh.plannerapp.presentation.planner.model

import io.github.aleksandersh.plannerapp.presentation.record.RecordViewModel

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
sealed class PlannerScreen {

    object Main : PlannerScreen()

    class NewRecord(val recordViewModel: RecordViewModel) : PlannerScreen()
}