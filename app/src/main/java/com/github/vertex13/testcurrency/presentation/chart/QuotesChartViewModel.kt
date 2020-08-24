package com.github.vertex13.testcurrency.presentation.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.domain.Quote
import com.github.vertex13.testcurrency.usecases.GetQuotesHistory
import kotlinx.coroutines.launch

class QuotesChartViewModel(
    private val currencyPair: CurrencyPair,
    private val getQuotesHistory: GetQuotesHistory
) : ViewModel() {

    private val _quotes = MutableLiveData<List<Quote>>(emptyList())
    val quotes: LiveData<List<Quote>> get() = _quotes

    init {
        loadQuotes()
    }

    private fun loadQuotes() = viewModelScope.launch {
        val quotes = getQuotesHistory(currencyPair)
        _quotes.postValue(quotes)
    }

}
