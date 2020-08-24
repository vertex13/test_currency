package com.github.vertex13.testcurrency.presentation.quotes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.vertex13.testcurrency.R
import com.github.vertex13.testcurrency.presentation.chart.QuotesChartFragment
import com.github.vertex13.testcurrency.presentation.common.BaseFragment
import com.github.vertex13.testcurrency.presentation.editpairs.EditCurrencyPairsFragment
import com.github.vertex13.testcurrency.presentation.quotes.adapter.QuotesAdapter
import kotlinx.android.synthetic.main.fragment_quotes.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuotesFragment : BaseFragment() {

    companion object {
        fun newInstance(): QuotesFragment = QuotesFragment()
    }

    private val viewModel by viewModel<QuotesViewModel>()

    private val adapter = QuotesAdapter(::onItemClick)

    override val layoutId: Int = R.layout.fragment_quotes

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            adapter = this@QuotesFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.quoteItems.observe(viewLifecycleOwner, Observer(adapter::setItems))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.quotes, menu)
    }

    private fun onItemClick(item: QuoteItem) {
        requireBaseActivity().pushFragment(QuotesChartFragment.newInstance(item.currencyPair))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editCurrencyPairs -> {
                requireBaseActivity().pushFragment(EditCurrencyPairsFragment.newInstance())
                true
            }
            R.id.sortAscent -> {
                viewModel.sort(CurrencyPairOrder.ASC)
                true
            }
            R.id.sortDescent -> {
                viewModel.sort(CurrencyPairOrder.DESC)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
