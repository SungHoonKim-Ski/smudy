package com.ssafy.presentation.ui.main

import android.os.Bundle
import android.view.View
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(
    { FragmentMainBinding.bind(it) }, R.layout.fragment_main
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}