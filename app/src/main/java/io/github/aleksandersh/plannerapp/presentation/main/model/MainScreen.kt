package io.github.aleksandersh.plannerapp.presentation.main.model

import io.github.aleksandersh.plannerapp.presentation.record.RecordViewModel
import io.github.aleksandersh.plannerapp.presentation.recordlist.RecordListViewModel
import io.github.aleksandersh.plannerapp.presentation.today.TodayViewModel

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
sealed class MainScreen {

    class Today(val viewModel: TodayViewModel) : MainScreen()

    class RecordList(val viewModel: RecordListViewModel) : MainScreen()

    class NewRecord(val viewModel: RecordViewModel) : MainScreen()
}