package com.ssafy.presentation.ui.study

import android.os.Bundle
import android.view.View
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentStudyBinding
import com.ssafy.presentation.model.Study
import com.ssafy.presentation.ui.study.adapter.StudyRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StudyFragment : BaseFragment<FragmentStudyBinding>(
    { FragmentStudyBinding.bind(it) }, R.layout.fragment_study
) {
    private val studyRecyclerAdapter = StudyRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        with(binding){
            rvStudyList.apply {
                adapter = studyRecyclerAdapter
                setHasFixedSize(true)
            }
            studyRecyclerAdapter.submitList(
                List(10){idx ->
                    Study("$idx","https://i.namu.wiki/i/eamDG79K8EwGm1iSqf0RQQlVdgQ_1vLnIBFsHQ3Qc5euveyneE_524R0lN-JcTHN3tHBcK-aKCkfvU7M8_ZiuZrcte904uhLYGzKIMcTXvzEv1IZuZn1ECuTCixvzPA8vDVTs_NvQ5l0DhEntgu-6A.webp","$idx","$idx" )
                }
            )
        }
    }
}