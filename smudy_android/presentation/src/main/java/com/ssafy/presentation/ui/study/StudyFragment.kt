package com.ssafy.presentation.ui.study

import android.os.Bundle
import android.view.View
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentStudyBinding
import com.ssafy.presentation.ui.study.adapter.StudyRecyclerAdapter


class StudyFragment : BaseFragment<FragmentStudyBinding>(
    { FragmentStudyBinding.bind(it) }, R.layout.fragment_study
) {
    private val studyRecyclerAdapter by lazy { StudyRecyclerAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        with(binding){
            rvStudyList.adapter = studyRecyclerAdapter
        }
    }
}