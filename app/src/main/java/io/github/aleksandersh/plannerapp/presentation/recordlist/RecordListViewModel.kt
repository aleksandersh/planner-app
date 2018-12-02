package io.github.aleksandersh.plannerapp.presentation.recordlist

import io.github.aleksandersh.plannerapp.presentation.main.MainRouter
import io.github.aleksandersh.plannerapp.presentation.recordlist.model.RecordListItem

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
class RecordListViewModel(private val mainRouter: MainRouter) {

    fun getItems(): List<RecordListItem> {
        return listOf(
            RecordListItem("Hello"),
            RecordListItem("Darkness,"),
            RecordListItem("my"),
            RecordListItem("old"),
            RecordListItem("friend"),
            RecordListItem(" "),
            RecordListItem("I've"),
            RecordListItem("come"),
            RecordListItem("to"),
            RecordListItem("talk"),
            RecordListItem("with"),
            RecordListItem("you"),
            RecordListItem("again"),
            RecordListItem(" "),
            RecordListItem("Because"),
            RecordListItem("a vision"),
            RecordListItem("softly"),
            RecordListItem("creeping"),
            RecordListItem(" "),
            RecordListItem("Left"),
            RecordListItem("its"),
            RecordListItem("seed"),
            RecordListItem("while"),
            RecordListItem("I"),
            RecordListItem("was"),
            RecordListItem("sleeping")
        )
    }

    fun onClickCreateRecord() {
        mainRouter.navigateRecordCreation()
    }
}