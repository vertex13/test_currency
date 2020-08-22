package com.github.vertex13.testcurrency.presentation.quotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.Quote
import com.github.vertex13.testcurrency.usecases.*
import kotlinx.coroutines.launch

class QuotesViewModel(
    getSubscribedCurrencyPairs: GetSubscribedCurrencyPairs,
    listenQuotes: ListenQuotes,
    trackSubscribedCurrencyPairs: TrackSubscribedCurrencyPairs,
    private val cancelListeningQuotes: CancelListeningQuotes,
    private val untrackSubscribedCurrencyPairs: UntrackSubscribedCurrencyPairs
) : ViewModel() {

    private val _quoteItems = MutableLiveData<List<QuoteItem>>(emptyList())
    val quoteItems: LiveData<List<QuoteItem>> get() = _quoteItems

    private var itemsOrder = CurrencyPairOrder.ASC

    init {
        viewModelScope.launch {
            val pairs = getSubscribedCurrencyPairs()
            onCurrencyPairsUpdated(pairs)
            listenQuotes(::onNewQuote)
            trackSubscribedCurrencyPairs(::onCurrencyPairsUpdated)
        }
    }

    override fun onCleared() {
        untrackSubscribedCurrencyPairs(::onCurrencyPairsUpdated)
        cancelListeningQuotes(::onNewQuote)
        super.onCleared()
    }

    fun sort(order: CurrencyPairOrder) {
        itemsOrder = order
        val newItems = _quoteItems.value!!.toMutableList().also {
            sortItems(it, itemsOrder)
        }
        _quoteItems.value = newItems
    }

    private fun sortItems(items: MutableList<QuoteItem>, order: CurrencyPairOrder) {
        val comparator = when (order) {
            CurrencyPairOrder.ASC ->
                compareBy<QuoteItem> { it.currencyPair.first }.thenBy { it.currencyPair.second }
            CurrencyPairOrder.DESC ->
                compareByDescending<QuoteItem> { it.currencyPair.second }.thenByDescending { it.currencyPair.second }
        }
        items.sortWith(comparator)
    }

    private fun onNewQuote(quote: Quote) {
        val newItems = _quoteItems.value!!.toMutableList()
        for (i in newItems.indices) {
            val item = newItems[i]
            if (quote.currencyPair == item.currencyPair) {
                newItems[i] = QuoteItem(item.currencyPair, quote)
                break
            }
        }
        _quoteItems.value = newItems
    }

    private fun onCurrencyPairsUpdated(pairs: List<CurrencyPair>) {
        val pairSet = pairs.toHashSet()
        val oldItems = _quoteItems.value!!
        val newItems = ArrayList<QuoteItem>(oldItems.size)
        for (item in oldItems) {
            if (pairSet.remove(item.currencyPair)) {
                newItems.add(item)
            }
        }
        for (pair in pairSet) {
            newItems.add(QuoteItem(pair, null))
        }
        sortItems(newItems, itemsOrder)
        _quoteItems.value = newItems
    }

}

enum class CurrencyPairOrder { ASC, DESC }

class QuoteItem(val currencyPair: CurrencyPair, val quote: Quote?)
