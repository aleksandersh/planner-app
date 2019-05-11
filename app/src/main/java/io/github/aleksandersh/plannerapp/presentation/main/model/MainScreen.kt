package io.github.aleksandersh.plannerapp.presentation.main.model

import io.github.aleksandersh.plannerapp.presentation.base.ViewScope
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewScope
import io.github.aleksandersh.plannerapp.presentation.recordlist.RecordListViewScope
import io.github.aleksandersh.plannerapp.presentation.today.TodayViewScope

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
sealed class MainScreen {

    abstract val viewScope: ViewScope

    class Today(override val viewScope: TodayViewScope) : MainScreen()

    class RecordList(override val viewScope: RecordListViewScope) : MainScreen()

    class NewRecord(override val viewScope: RecordViewScope) : MainScreen()
}