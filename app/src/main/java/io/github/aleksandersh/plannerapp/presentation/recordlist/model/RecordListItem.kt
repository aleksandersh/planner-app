package io.github.aleksandersh.plannerapp.presentation.recordlist.model

import android.view.View

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
data class RecordListItem(
    val id: Long,
    val title: String
) {

    val onClickItemListener: View.OnClickListener? get() = _onClickItemListener
    private var _onClickItemListener: View.OnClickListener? = null

    constructor(
        id: Long,
        title: String,
        onClickItemListener: View.OnClickListener
    ) : this(id, title) {
        _onClickItemListener = onClickItemListener
    }
}