package com.choco.browse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.choco.browse.databinding.ItemEntryPreviewBinding
import com.choco.browse.model.Entry
import com.choco.browse.view.viewholder.EntryPreviewViewHolder

class OrderEntriesAdapter(): RecyclerView.Adapter<EntryPreviewViewHolder>() {
    var items: List<Entry> = emptyList()
        set(newItems) {
            field = newItems
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryPreviewViewHolder {
        return EntryPreviewViewHolder(
            ItemEntryPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: EntryPreviewViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}