package io.github.aleksandersh.plannerapp.presentation.recordlist.model

import android.view.View

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
data class RecordListItem(
    val title: String,
    val onClickListener: View.OnClickListener
)