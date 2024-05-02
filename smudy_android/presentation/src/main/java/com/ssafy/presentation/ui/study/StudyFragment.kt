package com.ssafy.presentation.ui.study

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentStudyBinding
import com.ssafy.presentation.ui.study.adapter.StudyRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StudyFragment : BaseFragment<FragmentStudyBinding>(
    { FragmentStudyBinding.bind(it) }, R.layout.fragment_study
) {
    private val studyRecyclerAdapter by lazy { StudyRecyclerAdapter() }
    private val viewModel by viewModels<StudyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserve()
        initEvent()
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMusicList().collect {
                studyRecyclerAdapter.submitData(it)
            }
        }
    }

    private fun initView() {
        with(binding) {
            rvStudyList.apply {
                adapter = studyRecyclerAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun initEvent() {
        with(binding) {
            tvMusicAdd.setOnClickListener {
                findNavController().navigate(R.id.action_studyFragment_to_musicSearchFragment)
            }
        }
    }
}