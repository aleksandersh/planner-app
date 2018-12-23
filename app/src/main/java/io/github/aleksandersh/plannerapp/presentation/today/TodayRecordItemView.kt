package io.github.aleksandersh.plannerapp.presentation.today

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import io.github.aleksandersh.plannerapp.presentation.today.model.TodayRecordItem
import org.jetbrains.anko.*

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class TodayRecordItemView : AnkoComponent<ViewGroup> {

    private lateinit var layout: View
    private lateinit var titleTextView: TextView
    private lateinit var doneButton: Button

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        val dip8 = dip(8)
        val dip16 = dip(16)
        layout = verticalLayout {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            gravity = Gravity.CENTER
            titleTextView = textView {
                textSize = 16f
            }.lparams {
                horizontalMargin = dip16
                verticalMargin = dip8
            }
            frameLayout {
                doneButton = button {
                    text = "Done"
                }.lparams {
                    horizontalMargin = dip16
                    verticalMargin = dip8
                    gravity = Gravity.END
                }
            }
        }
        layout
    }

    fun bindItem(item: TodayRecordItem) {
        titleTextView.text = item.title
        layout.setOnClickListener(item.onClickItemListener)
        doneButton.setOnClickListener(item.onClickDoneListener)
    }
}