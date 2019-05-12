package io.github.aleksandersh.plannerapp.presentation.recordlist

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.aleksandersh.plannerapp.presentation.base.AnkoViewComponent
import io.github.aleksandersh.plannerapp.utils.observeNonNull
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
class RecordListViewComponent(
    context: Context,
    private val viewModel: RecordListViewScope
) : AnkoViewComponent<ViewGroup>(context) {

    private object ID {

        val recyclerViewRecords = ViewCompat.generateViewId()
    }

    private val recordsAdapter: RecordListAdapter = RecordListAdapter()

    override fun createView(ui: AnkoContext<Context>): ViewGroup = with(ui) {
        val dip8 = dip(8)
        val dip16 = dip(16)
        return linearLayout {
            backgroundColor = Color.WHITE
            orientation = LinearLayout.VERTICAL
            textView("Okey, Gogol, show me exists records").lparams(matchParent, wrapContent) {
                setMargins(dip8, dip16, dip8, dip16)
            }
            button("Create new") {
                setOnClickListener { viewModel.onClickCreateRecord() }
            }.lparams(matchParent, wrapContent) {
                setMargins(dip8, dip16, dip8, dip16)
            }
            recyclerView {
                id = ID.recyclerViewRecords
                adapter = recordsAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )
            }.lparams(matchParent, matchParent)
        }
    }

    override fun onAttach() {
        observeNonNull(viewModel.items) { items ->
            recordsAdapter.setItems(items)
            recordsAdapter.notifyDataSetChanged()
        }
    }
}