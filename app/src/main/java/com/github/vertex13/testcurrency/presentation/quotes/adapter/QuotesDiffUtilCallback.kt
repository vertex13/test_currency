package com.github.vertex13.testcurrency.presentation.quotes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.vertex13.testcurrency.presentation.quotes.QuoteItem

class QuotesDiffUtilCallback(
    private val oldItems: List<QuoteItem>,
    private val newItems: List<QuoteItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPos: Int, newItemPos: Int): Boolean {
        return oldItems[oldItemPos].currencyPair == newItems[newItemPos].currencyPair
    }

    override fun areContentsTheSame(oldItemPos: Int, newItemPos: Int): Boolean {
        val oldItem = oldItems[oldItemPos]
        val newItem = newItems[newItemPos]

        return oldItem.currencyPair == newItem.currencyPair
                && oldItem.quote == newItem.quote
    }

}
