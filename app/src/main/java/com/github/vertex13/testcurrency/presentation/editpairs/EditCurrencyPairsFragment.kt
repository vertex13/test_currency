package com.github.vertex13.testcurrency.presentation.editpairs

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.vertex13.testcurrency.R
import com.github.vertex13.testcurrency.presentation.common.BaseFragment
import com.github.vertex13.testcurrency.presentation.editpairs.adapter.EditCurrencyPairsAdapter
import kotlinx.android.synthetic.main.fragment_quotes.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCurrencyPairsFragment : BaseFragment() {

    companion object {
        fun newInstance(): EditCurrencyPairsFragment = EditCurrencyPairsFragment()
    }

    override val layoutId: Int = R.layout.fragment_edit_quotes

    private val viewModel by viewModel<EditQuotesViewModel>()

    private val adapter = EditCurrencyPairsAdapter(::onCheckItem)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            adapter = this@EditCurrencyPairsFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.currencyPairs.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
    }

    private fun onCheckItem(item: EditCurrencyPairItem, isChecked: Boolean) {
        if (isChecked) {
            viewModel.subscribe(item.currencyPair)
        } else {
            viewModel.unsubscribe(item.currencyPair)
        }
    }

}
