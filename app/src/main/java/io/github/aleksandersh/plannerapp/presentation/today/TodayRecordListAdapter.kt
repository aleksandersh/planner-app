package io.github.aleksandersh.plannerapp.presentation.today

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.aleksandersh.plannerapp.presentation.today.model.DiffUtilITodayItemCallback
import io.github.aleksandersh.plannerapp.presentation.today.model.TodayRecordItem
import org.jetbrains.anko.AnkoContext

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class TodayRecordListAdapter :
    ListAdapter<TodayRecordItem, TodayRecordListAdapter.RViewHolder>(DiffUtilITodayItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val ankoContext = AnkoContext.create(parent.context, parent)
        val component = TodayRecordItemView()
        return RViewHolder(component, component.createView(ankoContext))
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RViewHolder(
        private val component: TodayRecordItemView,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TodayRecordItem) {
            component.bindItem(item)
        }
    }
}