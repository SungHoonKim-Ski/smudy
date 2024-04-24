package com.ssafy.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseActivity
import com.ssafy.presentation.databinding.ActivityMainBinding
import com.ssafy.presentation.ui.history.HistoryFragment
import com.ssafy.presentation.ui.main.MainFragment
import com.ssafy.presentation.ui.study.StudyFragment

private const val TAG_HISTORY = "historyFragment"
private const val TAG_HOME = "homeFragment"
private const val TAG_STUDY = "studyFragment"

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment(TAG_HOME, MainFragment())
        binding.bnBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> setFragment(TAG_HOME, MainFragment())
                R.id.historyFragment -> setFragment(TAG_HISTORY, HistoryFragment())
                R.id.studyFragment -> setFragment(TAG_STUDY, StudyFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            fragTransaction.add(R.id.fl_fragment_container, fragment, tag)
        }

        val history = manager.findFragmentByTag(TAG_HISTORY)
        val home = manager.findFragmentByTag(TAG_HOME)
        val study = manager.findFragmentByTag(TAG_STUDY)

        if (history != null) {
            fragTransaction.hide(history)
        }

        if (home != null) {
            fragTransaction.hide(home)
        }

        if (study != null) {
            fragTransaction.hide(study)
        }

        if (tag == TAG_HISTORY) {
            if (history != null) {
                fragTransaction.show(history)
            }
        } else if (tag == TAG_HOME) {
            if (home != null) {
                fragTransaction.show(home)
            }
        } else if (tag == TAG_STUDY) {
            if (study != null) {
                fragTransaction.show(study)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}