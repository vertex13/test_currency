package com.github.vertex13.testcurrency.data

import com.github.vertex13.testcurrency.data.api.QuotesEccallsApi
import com.github.vertex13.testcurrency.data.db.AppDatabase
import com.github.vertex13.testcurrency.domain.CurrencyPairRepository
import com.github.vertex13.testcurrency.domain.CurrencyPairSubscriptionManager
import com.github.vertex13.testcurrency.domain.QuotesRepository
import com.github.vertex13.testcurrency.domain.QuotesStream
import org.koin.dsl.module

val dataModule = module {
    single<CurrencyPairRepository> { MockCurrencyPairRepository() }
    single<CurrencyPairSubscriptionManager> { LocalCurrencyPairSubscriptionManager(get(), get()) }
    single<QuotesStream> { QuotesStreamImpl(get(), get(), get()) }
    single<QuotesRepository> { LocalQuotesRepository() }
    single { QuotesEccallsApi() }
    single { AppDatabase.build(get()) }
}
