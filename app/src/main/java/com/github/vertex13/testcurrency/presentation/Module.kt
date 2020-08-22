package com.github.vertex13.testcurrency.presentation

import com.github.vertex13.testcurrency.presentation.quotes.QuotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { QuotesViewModel(get(), get(), get(), get(), get()) }
}