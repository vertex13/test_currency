package com.github.vertex13.testcurrency.presentation.common

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.vertex13.testcurrency.R

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENT_CONTAINER_ID = R.id.fragment_container
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener(::onBackStackChanged)
    }

    override fun onDestroy() {
        supportFragmentManager.removeOnBackStackChangedListener(::onBackStackChanged)
        super.onDestroy()
    }

    fun pushFragment(fragment: Fragment) {
        if (findViewById<View>(FRAGMENT_CONTAINER_ID) == null) {
            throw IllegalStateException("Activity $this does not have a fragment container.")
        }
        supportFragmentManager.beginTransaction()
            .replace(FRAGMENT_CONTAINER_ID, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        val fragmentsInStack = supportFragmentManager.backStackEntryCount
        when {
            fragmentsInStack > 1 -> supportFragmentManager.popBackStack()
            fragmentsInStack == 1 -> finish()
            else -> super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onBackStackChanged() {
        val backStackSize = supportFragmentManager.backStackEntryCount
        supportActionBar?.setDisplayHomeAsUpEnabled(backStackSize > 1)
    }

}
