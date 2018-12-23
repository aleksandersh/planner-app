package io.github.aleksandersh.plannerapp.presentation.today

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import io.github.aleksandersh.plannerapp.presentation.today.model.TodayRecordItem
import io.github.aleksandersh.plannerapp.utils.dip

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class TodayRecordItemView : LinearLayoutCompat {

    private val titleTextView: TextView
    private val doneButton: AppCompatButton

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        val dip8 = dip(8)
        val dip16 = dip(16)
        layoutParams = LinearLayoutCompat.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        orientation = LinearLayoutCompat.VERTICAL
        gravity = Gravity.CENTER
        titleTextView = TextView(context).apply {
            textSize = 16f
        }
        addView(
            titleTextView,
            LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(dip16, dip8, dip16, dip8)
            }
        )
        addView(
            FrameLayout(context).apply {
                gravity = Gravity.END
                doneButton = AppCompatButton(context).apply {
                    text = "DONE"
                }
                addView(
                    doneButton,
                    FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                        setMargins(dip16, dip8, dip16, dip8)
                    }
                )
            },
            LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        )
    }

    fun bindItem(item: TodayRecordItem) {
        titleTextView.text = item.title
        setOnClickListener(item.onClickItemListener)
        doneButton.setOnClickListener(item.onClickDoneListener)
    }
}