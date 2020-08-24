package com.github.vertex13.testcurrency.presentation.chart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.vertex13.testcurrency.R
import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.Quote
import com.github.vertex13.testcurrency.presentation.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_quotes_chart.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class QuotesChartFragment : BaseFragment() {

    companion object {
        private const val ARG_CURRENCY_PAIR = "arg_currency_pair"

        fun newInstance(currencyPair: CurrencyPair): QuotesChartFragment {
            val bundle = Bundle().apply {
                putString(ARG_CURRENCY_PAIR, currencyPairToString(currencyPair))
            }
            return QuotesChartFragment().apply {
                arguments = bundle
            }
        }

        private fun currencyPairToString(currencyPair: CurrencyPair): String {
            return currencyPair.first + currencyPair.second
        }

        private fun restoreCurrencyPair(str: String): CurrencyPair {
            val first = str.substring(0, 3)
            val second = str.substring(3, 6)
            return CurrencyPair(first, second)
        }
    }

    override val layoutId: Int = R.layout.fragment_quotes_chart

    private lateinit var viewModel: QuotesChartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currencyPair = arguments?.getString(ARG_CURRENCY_PAIR)?.let { restoreCurrencyPair(it) }
        viewModel = getViewModel { parametersOf(currencyPair) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chartView.apply {
            setTouchEnabled(true)
            setPinchZoom(true)
            description.isEnabled = false
            axisRight.isEnabled = false
        }

        viewModel.quotes.observe(viewLifecycleOwner, Observer(::updateChart))
    }

    private fun updateChart(quotes: List<Quote>) {
        val minDate = quotes.firstOrNull()?.timestamp ?: Date(0L)
        val xAxis = chartView.xAxis
        xAxis.valueFormatter = DateFormatter(minDate)
        xAxis.setLabelCount(4, true)

        val bidEntries = ArrayList<Entry>(quotes.size)
        val askEntries = ArrayList<Entry>(quotes.size)

        quotes.forEach { quote ->
            val fTime = dateToFloat(quote.timestamp, minDate)
            bidEntries.add(Entry(fTime, quote.bid.toFloat()))
            askEntries.add(Entry(fTime, quote.ask.toFloat()))
        }

        val bidSet = createLine(bidEntries, Color.BLUE, getString(R.string.quotes_chart_bid_label))
        val askSet = createLine(askEntries, Color.RED, getString(R.string.quotes_chart_ask_label))

        chartView.data = LineData(listOf(bidSet, askSet))
        chartView.invalidate()
    }

    private fun createLine(entries: List<Entry>, color: Int, label: String): LineDataSet {
        return LineDataSet(entries, label).apply {
            setColor(color)
            lineWidth = 1f
            setDrawCircles(false)
        }
    }

}

private fun dateToFloat(date: Date, minDate: Date): Float {
    return (date.time - minDate.time) * 0.001f
}

private fun floatToDate(f: Float, minDate: Date): Date {
    return Date((f * 1000).toLong() + minDate.time)
}

class DateFormatter(private val minDate: Date) : ValueFormatter() {

    private val formatter = SimpleDateFormat("d MMM H:mm", Locale.getDefault())

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return formatter.format(floatToDate(value, minDate))
    }

}
