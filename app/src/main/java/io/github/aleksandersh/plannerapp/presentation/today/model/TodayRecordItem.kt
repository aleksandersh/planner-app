package io.github.aleksandersh.plannerapp.presentation.today.model

import android.view.View

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
data class TodayRecordItem(
    val id: Long,
    val title: String,
    val isDone: Boolean
) {

    val onClickDoneListener: View.OnClickListener? get() = _onClickDoneListener
    val onClickItemListener: View.OnClickListener? get() = _onClickItemListener
    private var _onClickDoneListener: View.OnClickListener? = null
    private var _onClickItemListener: View.OnClickListener? = null

    constructor(
        id: Long,
        title: String,
        isDone: Boolean,
        onClickDoneListener: View.OnClickListener,
        onClickItemListener: View.OnClickListener
    ) : this(id, title, isDone) {
        _onClickDoneListener = onClickDoneListener
        _onClickItemListener = onClickItemListener
    }
}