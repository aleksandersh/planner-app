package io.github.aleksandersh.plannerapp.presentation.today.model

import android.view.View

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
data class TodayRecordItem(
    val id: Long,
    val title: String,
    val description: String
) {

    val onClickItemListener: View.OnClickListener? get() = _onClickItemListener
    val onClickDoneListener: View.OnClickListener? get() = _onClickDoneListener
    private var _onClickItemListener: View.OnClickListener? = null
    private var _onClickDoneListener: View.OnClickListener? = null

    constructor(
        id: Long,
        title: String,
        description: String,
        onClickItemListener: View.OnClickListener,
        onClickDoneListener: View.OnClickListener
    ) : this(id, title, description) {
        _onClickItemListener = onClickItemListener
        _onClickDoneListener = onClickDoneListener
    }
}