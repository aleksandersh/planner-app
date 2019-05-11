package io.github.aleksandersh.plannerapp.presentation.recordlist

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import io.github.aleksandersh.plannerapp.presentation.recordlist.model.RecordListItem
import io.github.aleksandersh.plannerapp.utils.dip

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
class RecordItemView : LinearLayout {

    private val titleTextView: TextView

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        val dip16 = context.dip(16)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        titleTextView = TextView(context).apply {
            textSize = 16f
        }
        addView(
            titleTextView,
            LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(dip16, dip16, dip16, dip16)
            }
        )
    }

    fun setRecordItem(item: RecordListItem) {
        titleTextView.text = item.title
        setOnClickListener(item.onClickItemListener)
    }
}