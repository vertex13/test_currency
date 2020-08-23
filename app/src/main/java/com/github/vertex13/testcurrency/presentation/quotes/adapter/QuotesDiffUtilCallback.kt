package com.github.vertex13.testcurrency.presentation.quotes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.vertex13.testcurrency.domain.Quote
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
                && areQuotesTheSame(oldItem.quote, newItem.quote)
    }

    private fun areQuotesTheSame(oldQuote: Quote?, newQuote: Quote?): Boolean {
        return if (oldQuote == null && newQuote == null) {
            true
        } else if (oldQuote != null && newQuote != null) {
            (oldQuote.bid == newQuote.bid
                    && oldQuote.ask == newQuote.ask
                    && oldQuote.spread == newQuote.spread)
        } else {
            false
        }
    }

}
