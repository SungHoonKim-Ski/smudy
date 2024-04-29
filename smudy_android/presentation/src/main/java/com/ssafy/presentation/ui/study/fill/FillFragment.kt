package com.ssafy.presentation.ui.study.fill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentFillBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FillFragment : BaseFragment<FragmentFillBinding>(
    { FragmentFillBinding.bind(it) }, R.layout.fragment_fill
) {

}