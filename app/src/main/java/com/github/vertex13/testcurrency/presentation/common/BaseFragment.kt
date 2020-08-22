package com.github.vertex13.testcurrency.presentation.common

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    fun requireBaseActivity(): BaseActivity {
        val baseActivity = requireActivity()
        if (baseActivity !is BaseActivity) {
            throw IllegalStateException("Fragment $this not attached to a BaseActivity.")
        }
        return baseActivity
    }

}
