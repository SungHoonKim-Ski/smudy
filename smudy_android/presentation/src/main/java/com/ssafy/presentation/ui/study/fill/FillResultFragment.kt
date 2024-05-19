package com.ssafy.presentation.ui.study.fill

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentFillResultBinding
import com.ssafy.presentation.ui.MainActivityViewModel
import com.ssafy.presentation.ui.study.adapter.BlankResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FillResultFragment(
) : BaseFragment<FragmentFillResultBinding>(
    { FragmentFillResultBinding.bind(it) }, R.layout.fragment_fill_result
) {

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private val resultAdapter = BlankResultAdapter()
    private val args: FillResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    override fun onStart() {
        super.onStart()
        mainActivityViewModel.setIsNavigationBarVisible(false)
    }

    override fun onStop() {
        super.onStop()
        mainActivityViewModel.setIsNavigationBarVisible(true)
    }

    private fun initView(){
        resultAdapter.submitList(args.ResultArgs.result.result)
        with(binding){
            tvNumOfQuestions.text = args.ResultArgs.result.totalSize.toString()
            tvNumOfCorrect.text = "${args.ResultArgs.result.score} / "
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

    private fun initEvent(){
        with(binding){
            btnConfirm.setOnClickListener {
                if (args.IsHistory){
                    findNavController().popBackStack()
                } else {
                    findNavController().navigate(R.id.action_fillResultFragment_to_studyFragment)
                }
            }
        }
    }
}