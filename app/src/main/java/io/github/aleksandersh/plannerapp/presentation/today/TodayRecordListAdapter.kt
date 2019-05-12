package io.github.aleksandersh.plannerapp.presentation.today

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.aleksandersh.plannerapp.presentation.today.model.DiffUtilITodayItemCallback
import io.github.aleksandersh.plannerapp.presentation.today.model.TodayRecordItem

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class TodayRecordListAdapter :
    ListAdapter<TodayRecordItem, TodayRecordListAdapter.RViewHolder>(DiffUtilITodayItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        return RViewHolder(TodayRecordWidget(parent))
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RViewHolder(
        private val component: TodayRecordWidget
    ) : RecyclerView.ViewHolder(component.view) {

        fun bind(item: TodayRecordItem) {
            component.bindItem(item)
        }
    }
}