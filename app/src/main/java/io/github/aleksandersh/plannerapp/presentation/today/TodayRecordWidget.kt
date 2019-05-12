package io.github.aleksandersh.plannerapp.presentation.today

import android.graphics.Color
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import io.github.aleksandersh.plannerapp.R
import io.github.aleksandersh.plannerapp.presentation.today.model.TodayRecordItem
import org.jetbrains.anko.*

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class TodayRecordWidget(parent: ViewGroup) : AnkoComponent<ViewGroup> {

    val view: View get() = layout

    private val ankoContext = AnkoContext.create(parent.context, parent)
    private val layout = createView(ankoContext)
    private lateinit var titleTextView: TextView
    private lateinit var subtitleTextView: TextView
    private lateinit var doneButton: Button

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        val dip8 = dip(8)
        val dip16 = dip(16)
        linearLayout {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(0, dip16, 0, dip16)
            verticalLayout {
                titleTextView = textView {
                    textColor = Color.BLACK
                    textSize = 16f
                }.lparams(wrapContent, wrapContent) {
                    leftMargin = dip16
                }
                subtitleTextView = textView {
                    textSize = 14f
                    maxLines = 2
                    ellipsize = TextUtils.TruncateAt.END
                }.lparams(wrapContent, wrapContent) {
                    topMargin = dip8
                    leftMargin = dip16
                }
            }.lparams(0, wrapContent, 1f) {
                gravity = Gravity.CENTER_VERTICAL
            }
            doneButton = button("Done") {
                background = null
                textColor = context.theme.color(R.attr.colorAccent)
            }.lparams(wrapContent, wrapContent) {
                horizontalMargin = dip16
                gravity = Gravity.CENTER_VERTICAL
            }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    fun bindItem(item: TodayRecordItem) {
        titleTextView.text = item.title
        subtitleTextView.text = item.description
        subtitleTextView.isGone = item.description.isEmpty()
        layout.setOnClickListener(item.onClickItemListener)
        doneButton.setOnClickListener(item.onClickDoneListener)
    }
}