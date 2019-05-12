package io.github.aleksandersh.plannerapp.presentation.recordlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.aleksandersh.plannerapp.presentation.recordlist.model.RecordListItem

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
class RecordListAdapter : RecyclerView.Adapter<RecordListAdapter.ItemViewHolder>() {

    private var items: List<RecordListItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(RecordItemWidget(parent))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<RecordListItem>) {
        this.items = items
    }

    class ItemViewHolder(
        private val widget: RecordItemWidget
    ) : RecyclerView.ViewHolder(widget.view) {

        fun bind(item: RecordListItem) {
            widget.setRecordItem(item)
        }
    }
}