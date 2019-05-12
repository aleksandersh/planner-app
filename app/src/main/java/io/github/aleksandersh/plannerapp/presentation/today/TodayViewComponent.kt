package io.github.aleksandersh.plannerapp.presentation.today

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.aleksandersh.plannerapp.R
import io.github.aleksandersh.plannerapp.presentation.base.AnkoViewComponent
import io.github.aleksandersh.plannerapp.utils.observeNonNull
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class TodayViewComponent(
    context: Context,
    private val viewModel: TodayViewScope
) : AnkoViewComponent<ViewGroup>(context) {

    private object ID {

        val recyclerViewRecords = ViewCompat.generateViewId()
    }

    private val recordListAdapter: TodayRecordListAdapter = TodayRecordListAdapter()

    override fun buildAnkoView(ui: AnkoContext<Context>): ViewGroup = with(ui) {
        val dip4 = dip(4)
        val dip8 = dip(8)
        val dip16 = dip(16)
        verticalLayout {
            recyclerView {
                id = ID.recyclerViewRecords
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = recordListAdapter
                addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )
            }.lparams(matchParent, 0) {
                weight = 1f
            }
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                elevation = dip4.toFloat()
                backgroundColor = context.theme.color(R.attr.colorPrimary)
                setPadding(0, dip4, 0, 0)
                button("Create") {
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.background_button_medium_emphasis_dark
                    setOnClickListener { viewModel.onClickCreateRecord() }
                }.lparams(0, wrapContent) {
                    weight = 1f
                    horizontalMargin = dip16
                    verticalMargin = dip8
                }
                button("Show all") {
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.background_button_medium_emphasis_dark
                    setOnClickListener { viewModel.onClickShowAllRecords() }
                }.lparams(0, wrapContent) {
                    weight = 1f
                    horizontalMargin = dip16
                    verticalMargin = dip8
                }
            }.lparams(matchParent, wrapContent) {
                topMargin = 8
            }
        }
    }

    override fun onAttach() {
        observeNonNull(viewModel.items, recordListAdapter::submitList)
    }
}