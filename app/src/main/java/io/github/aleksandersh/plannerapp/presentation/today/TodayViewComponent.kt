package io.github.aleksandersh.plannerapp.presentation.today

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.aleksandersh.plannerapp.presentation.ViewComponent
import io.github.aleksandersh.plannerapp.utils.dip
import io.github.aleksandersh.plannerapp.utils.observe

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class TodayViewComponent(
    private val context: Context,
    private val viewModel: TodayViewModel
) : ViewComponent<ViewGroup>() {

    private val recordListAdapter: TodayRecordListAdapter = TodayRecordListAdapter()

    override fun buildView(): ViewGroup {
        val dip8 = context.dip(8)
        val dip16 = context.dip(16)
        return LinearLayoutCompat(context).apply {
            orientation = LinearLayoutCompat.VERTICAL
            addView(
                LinearLayoutCompat(context).apply {
                    orientation = LinearLayoutCompat.HORIZONTAL
                    addView(
                        AppCompatButton(context).apply {
                            text = "Create"
                            setOnClickListener { viewModel.onClickCreateRecord() }
                        },
                        LinearLayoutCompat.LayoutParams(0, WRAP_CONTENT).apply {
                            weight = 1f
                            setMargins(dip16, dip8, dip16, dip8)
                        }
                    )
                    addView(
                        AppCompatButton(context).apply {
                            text = "Show all"
                            setOnClickListener { viewModel.onClickShowAllRecords() }
                        },
                        LinearLayoutCompat.LayoutParams(0, WRAP_CONTENT).apply {
                            weight = 1f
                            setMargins(dip16, dip8, dip16, dip8)
                        }
                    )
                },
                LinearLayoutCompat.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            )
            addView(
                RecyclerView(context).apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter = recordListAdapter
                    addItemDecoration(
                        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    )
                },
                LinearLayoutCompat.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            )
        }
    }

    override fun onAttach() {
        viewModel.items.observe(this, recordListAdapter::submitList)
    }
}