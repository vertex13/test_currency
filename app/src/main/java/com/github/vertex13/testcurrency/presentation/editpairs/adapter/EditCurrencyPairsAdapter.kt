package com.github.vertex13.testcurrency.presentation.editpairs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vertex13.testcurrency.presentation.editpairs.EditCurrencyPairItem

class EditCurrencyPairsAdapter(
    private val onItemChecked: ((EditCurrencyPairItem, Boolean) -> Unit)?
) : RecyclerView.Adapter<EditCurrencyPairViewHolder>() {

    private var items = emptyList<EditCurrencyPairItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditCurrencyPairViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(EditCurrencyPairViewHolder.LAYOUT_ID, parent, false)
            .let { EditCurrencyPairViewHolder(it) }
    }

    override fun onBindViewHolder(holder: EditCurrencyPairViewHolder, position: Int) {
        holder.bind(items[position], onItemChecked)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<EditCurrencyPairItem>) {
        items = newItems
        notifyDataSetChanged()
    }

}
