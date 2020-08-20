package com.github.vertex13.testcurrency.presentation.quotes

import com.github.vertex13.testcurrency.R
import com.github.vertex13.testcurrency.presentation.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuotesFragment : BaseFragment() {

    companion object {
        fun newInstance(): QuotesFragment = QuotesFragment()
    }

    private val model: QuotesViewModel by viewModel()

    override val layoutId: Int = R.layout.fragment_quotes

}
