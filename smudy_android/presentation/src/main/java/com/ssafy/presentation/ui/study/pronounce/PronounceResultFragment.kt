package com.ssafy.presentation.ui.study.pronounce

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentPronounceResultBinding


class PronounceResultFragment : BaseFragment<FragmentPronounceResultBinding>(
    { FragmentPronounceResultBinding.bind(it)}, R.layout.fragment_pronounce_result
) {
    private val parentViewModel:PronounceProblemViewModel by hiltNavGraphViewModels(R.id.nav_pronounce)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}