package com.ssafy.presentation.ui.study.shuffle

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.intIntMapOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ssafy.domain.model.ShuffleQuestionProblem
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentShuffleResultBinding
import com.ssafy.presentation.ui.MainActivityViewModel
import com.ssafy.presentation.ui.study.fill.FillResultFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShuffleResultFragment : BaseFragment<FragmentShuffleResultBinding>(
    { FragmentShuffleResultBinding.bind(it) }, R.layout.fragment_shuffle_result
) {

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val correctAdapter = ShuffleResultAdapter()
    private val wrongAdapter = ShuffleResultAdapter(false)
    private val args: ShuffleResultFragmentArgs by navArgs()

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

    @SuppressLint("SetTextI18n")
    private fun initView() {
        with(binding){
            rvCorrect.adapter = correctAdapter
            rvIncorrect.adapter = wrongAdapter
            with(args.ResultArgs){
                tvScore.text = score.toString()
                tvNumOfQuestions.text = " / $totalSize"
                with(loBasic){
                    tvAlbumTitle.text = title
                    tvAlbumSinger.text = artist
                    Glide.with(requireContext())
                        .load(jacket)
                        .into(ivAlbumJacket)
                }
            }
        }

        correctAdapter.submitList(args.ResultArgs.correct.map {
            ShuffleQuestionProblem(
                it.lyricSentenceEn, it.lyricSentenceKo, it.lyricSentenceEn
            )
        })

        wrongAdapter.submitList(args.ResultArgs.wrong.map {
            ShuffleQuestionProblem(
                it.lyricSentenceEn, it.lyricSentenceKo, it.userLyricSentence
            )
        })
    }

    private fun initEvent(){
        with(binding){
            btnConfirm.setOnClickListener {
                if (args.IsHistory){
                    findNavController().popBackStack()
                } else {
                    findNavController().navigate(R.id.action_shuffleResultFragment_to_studyFragment)
                }
            }
        }
    }
}