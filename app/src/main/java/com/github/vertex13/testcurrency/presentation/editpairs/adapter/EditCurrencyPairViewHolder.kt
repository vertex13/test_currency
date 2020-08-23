package com.github.vertex13.testcurrency.presentation.editpairs.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.vertex13.testcurrency.R
import com.github.vertex13.testcurrency.presentation.editpairs.EditCurrencyPairItem
import kotlinx.android.synthetic.main.view_edit_currency_pair.view.*

class EditCurrencyPairViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        const val LAYOUT_ID = R.layout.view_edit_currency_pair
    }

    fun bind(item: EditCurrencyPairItem, onChecked: ((EditCurrencyPairItem, Boolean) -> Unit)?) {
        itemView.apply {
            vecp_titleView.text = context.getString(
                R.string.edit_currency_item_title, item.currencyPair.first, item.currencyPair.second
            )
            vecp_checkBox.isChecked = item.isChecked
            vecp_checkBox.setOnClickListener {
                onChecked?.invoke(item, vecp_checkBox.isChecked)
            }
        }
    }

}
