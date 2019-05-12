package io.github.aleksandersh.plannerapp.presentation.recordlist

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.github.aleksandersh.plannerapp.presentation.recordlist.model.RecordListItem
import org.jetbrains.anko.*

/**
 * Created on 12.05.2019.
 * @author AleksanderSh
 */
class RecordItemWidget(parent: ViewGroup) : AnkoComponent<ViewGroup> {

    val view: View get() = layout

    private val ankoContext = AnkoContext.create(parent.context, parent)
    private val layout = createView(ankoContext)
    private lateinit var titleTextView: TextView

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        val dip16 = dip(16)
        linearLayout {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            titleTextView = textView {
                textSize = 16f
            }.lparams(wrapContent, wrapContent) {
                setMargins(dip16, dip16, dip16, dip16)
            }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    fun setRecordItem(item: RecordListItem) {
        titleTextView.text = item.title
        layout.setOnClickListener(item.onClickItemListener)
    }
}