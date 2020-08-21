package com.github.vertex13.testcurrency.usecases

import org.koin.dsl.module

val useCasesModule = module {
    factory { SubscribeToCurrencyPair(get()) }
    factory { UnsubscribeFromCurrencyPair(get()) }
    factory { SubscribeToQuotesStream(get()) }
    factory { UnsubscribeFromQuotesStream(get()) }
    factory { GetQuotesHistory(get()) }
}
