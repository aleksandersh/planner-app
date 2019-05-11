package io.github.aleksandersh.plannerapp.presentation.recordlist

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.aleksandersh.plannerapp.presentation.base.ViewComponent
import io.github.aleksandersh.plannerapp.utils.*

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
class RecordListViewComponent(
    private val context: Context,
    private val viewModel: RecordListViewScope
) : ViewComponent<ViewGroup>() {

    private object ID {

        val recyclerViewRecords = ViewCompat.generateViewId()
    }

    override val layoutParams: ViewGroup.LayoutParams =
        frameLayoutParams(MATCH_PARENT, MATCH_PARENT) {
            gravity = Gravity.CENTER
        }

    private val recordsAdapter: RecordListAdapter = RecordListAdapter(context)

    override fun buildView(): ViewGroup = with(context) {
        val dip8 = dip(8)
        val dip16 = dip(16)
        LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(
                textView {
                    text = "Okey, Gogol, show me exists records"
                },
                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                    setMargins(dip8, dip16, dip8, dip16)
                }
            )
            addView(
                button {
                    text = "Create new"
                    setOnClickListener { viewModel.onClickCreateRecord() }
                },
                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                    setMargins(dip8, dip16, dip8, dip16)
                }
            )
            addView(
                RecyclerView(context).apply {
                    id = ID.recyclerViewRecords
                    adapter = recordsAdapter
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    addItemDecoration(
                        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    )
                },
                LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            )
        }
    }

    override fun onAttach() {
        viewModel.items.observe(this) { items ->
            recordsAdapter.setItems(items)
            recordsAdapter.notifyDataSetChanged()
        }
    }
}