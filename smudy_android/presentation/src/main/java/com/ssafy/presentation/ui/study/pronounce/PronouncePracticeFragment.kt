package com.ssafy.presentation.ui.study.pronounce

import androidx.fragment.app.viewModels
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentPronouncePracticeBinding


class PronouncePracticeFragment : BaseFragment<FragmentPronouncePracticeBinding>(
    { FragmentPronouncePracticeBinding.bind(it)}, R.layout.fragment_pronounce_practice
) {
    private val viewModel by viewModels<PronouncePracticeViewModel>()
}