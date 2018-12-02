package io.github.aleksandersh.plannerapp.presentation.recordlist

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.aleksandersh.plannerapp.R
import io.github.aleksandersh.plannerapp.presentation.ViewComponent
import io.github.aleksandersh.plannerapp.utils.button
import io.github.aleksandersh.plannerapp.utils.dip
import io.github.aleksandersh.plannerapp.utils.linearLayoutParams
import io.github.aleksandersh.plannerapp.utils.textView

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
class RecordListViewComponent(
    private val context: Context,
    private val viewModel: RecordListViewModel
) : ViewComponent<ViewGroup>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recordsAdapter: RecordListAdapter

    override fun buildView(): ViewGroup = with(context) {
        val dip4 = dip(4)
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
            recordsAdapter = RecordListAdapter(context, viewModel.getItems())
            recyclerView = RecyclerView(context).apply {
                id = R.id.record_list_recycler_view
                adapter = recordsAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
            addView(
                recyclerView,
                LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            )
        }
    }
}