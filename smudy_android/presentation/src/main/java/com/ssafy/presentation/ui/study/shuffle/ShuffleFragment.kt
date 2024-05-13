package com.ssafy.presentation.ui.study.shuffle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentShuffleBinding


class ShuffleFragment : BaseFragment<FragmentShuffleBinding>(
    { FragmentShuffleBinding.bind(it)}, R.layout.fragment_shuffle
){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){

    }

}