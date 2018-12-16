package io.github.aleksandersh.plannerapp.presentation.today.model

import androidx.recyclerview.widget.DiffUtil

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class DiffUtilITodayItemCallback : DiffUtil.ItemCallback<TodayRecordItem>() {

    override fun areItemsTheSame(oldItem: TodayRecordItem, newItem: TodayRecordItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodayRecordItem, newItem: TodayRecordItem): Boolean {
        return oldItem == newItem
    }
}