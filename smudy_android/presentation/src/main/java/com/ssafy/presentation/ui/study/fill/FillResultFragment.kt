package com.ssafy.presentation.ui.study.fill

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentFillResultBinding
import com.ssafy.presentation.ui.study.adapter.BlankResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FillResultFragment(
) : BaseFragment<FragmentFillResultBinding>(
    { FragmentFillResultBinding.bind(it) }, R.layout.fragment_fill_result
) {

    private val resultAdapter = BlankResultAdapter()
    private val args: FillResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        resultAdapter.submitList(args.ResultArgs.result.result)
        with(binding){
            tvNumOfQuestions.text = args.ResultArgs.result.totalSize.toString()
            tvNumOfCorrect.text = args.ResultArgs.result.score.toString()
            rvLyrics.apply{
                adapter = resultAdapter
            }

            with(loBasic){
                tvAlbumTitle.text = args.ResultArgs.title
                tvAlbumSinger.text = args.ResultArgs.artist
                Glide.with(requireContext())
                   .load(args.ResultArgs.jacket)
                   .into(ivAlbumJacket)
            }
        }
    }

}