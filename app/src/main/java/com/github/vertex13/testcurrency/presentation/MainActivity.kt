package com.github.vertex13.testcurrency.presentation

import android.os.Bundle
import com.github.vertex13.testcurrency.R
import com.github.vertex13.testcurrency.presentation.common.BaseActivity
import com.github.vertex13.testcurrency.presentation.quotes.QuotesFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            pushFragment(QuotesFragment.newInstance())
        }
    }
}