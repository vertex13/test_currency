package com.github.vertex13.testcurrency.presentation.editpairs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.vertex13.testcurrency.domain.CurrencyPair
import com.github.vertex13.testcurrency.usecases.GetAllCurrencyPairs
import com.github.vertex13.testcurrency.usecases.GetSubscribedCurrencyPairs
import com.github.vertex13.testcurrency.usecases.SubscribeToCurrencyPair
import com.github.vertex13.testcurrency.usecases.UnsubscribeFromCurrencyPair
import kotlinx.coroutines.launch

class EditQuotesViewModel(
    private val getSubscribedCurrencyPairs: GetSubscribedCurrencyPairs,
    private val getAllCurrencyPairs: GetAllCurrencyPairs,
    private val subscribeToCurrencyPair: SubscribeToCurrencyPair,
    private val unsubscribeFromCurrencyPair: UnsubscribeFromCurrencyPair
) : ViewModel() {

    private val _currencyPairs = MutableLiveData<List<EditCurrencyPairItem>>(emptyList())
    val currencyPairs: LiveData<List<EditCurrencyPairItem>> get() = _currencyPairs

    init {
        loadCurrencyPairs()
    }

    fun subscribe(currencyPair: CurrencyPair) = viewModelScope.launch {
        subscribeToCurrencyPair(currencyPair)
    }

    fun unsubscribe(currencyPair: CurrencyPair) = viewModelScope.launch {
        unsubscribeFromCurrencyPair(currencyPair)
    }

    private fun loadCurrencyPairs() = viewModelScope.launch {
        val allPairs = getAllCurrencyPairs()
        val subPairSet = getSubscribedCurrencyPairs().toHashSet()
        val items = allPairs.map { pair -> EditCurrencyPairItem(pair, subPairSet.contains(pair)) }
        _currencyPairs.postValue(items)
    }

}

class EditCurrencyPairItem(val currencyPair: CurrencyPair, var isChecked: Boolean)
