package com.github.vertex13.testcurrency.presentation.quotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.vertex13.testcurrency.presentation.quotes.QuoteItem

class QuotesAdapter(
    private val onItemClick: ((QuoteItem) -> Unit)?
) : RecyclerView.Adapter<QuoteViewHolder>() {

    private var items = emptyList<QuoteItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(QuoteViewHolder.LAYOUT_ID, parent, false)
            .let { QuoteViewHolder(it) }
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<QuoteItem>) {
        if (items === newItems) {
            return
        }
        val diffResult = DiffUtil.calculateDiff(QuotesDiffUtilCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

}
