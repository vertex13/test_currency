package com.github.vertex13.testcurrency.data

import com.github.vertex13.testcurrency.domain.CurrencyPairRepository
import org.koin.dsl.module

val dataModule = module {
    single<CurrencyPairRepository> { MockCurrencyPairRepository() }
}
