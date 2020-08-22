package com.github.vertex13.testcurrency.presentation.quotes.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.vertex13.testcurrency.R
import com.github.vertex13.testcurrency.presentation.quotes.QuoteItem
import kotlinx.android.synthetic.main.view_quote.view.*

class QuoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        const val LAYOUT_ID = R.layout.view_quote
    }

    fun bind(item: QuoteItem, onItemClick: ((QuoteItem) -> Unit)?) {
        itemView.apply {
            setOnClickListener { onItemClick?.invoke(item) }
            vq_instrumentView.text = context.getString(
                R.string.quote_item_instrument, item.currencyPair.first, item.currencyPair.second
            )
            if (item.quote != null) {
                vq_bidAskView.text = context.getString(
                    R.string.quote_item_bid_ask, item.quote.bid, item.quote.ask
                )
                vq_spreadView.text = context.getString(
                    R.string.quote_item_spread, item.quote.spread
                )
            } else {
                vq_bidAskView.text = null
                vq_spreadView.text = null
            }
        }
    }

}
