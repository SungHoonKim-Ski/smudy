package com.ssafy.presentation.ui.study.express

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.ssafy.presentation.databinding.DialogExpressResultBinding
import com.ssafy.presentation.model.Music
import com.ssafy.presentation.model.express.ExpressResult

class ExpressResultDialog(
    private val expressResult: ExpressResult,
    private val count: Int,
    private val music: Music,
    private val context: Context,
    private val onDisMiss: (Int) -> (Unit)
) : DialogFragment() {
    private var _binding: DialogExpressResultBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            // 화면 너비에 맞춰 다이얼로그의 너비 설정
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogExpressResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    private fun initView() {
        with(binding) {
            tvRecommendedEnglish.text = expressResult.suggestedSentenceEn
            tvRecommendedKorean.text = expressResult.suggestedSentenceKo
            tvAnswerEnglish.text = expressResult.userAnswerSentenceEn
            tvAnswerKorean.text = expressResult.userAnswerSentenceKo
            tvAlbumTitle.text = music.title
            tvAlbumSinger.text = music.artist
            tvProgress.text = (count + 1).toString()
            tvScore.text = expressResult.score.toString()
            Glide.with(context).load(music.jacket).into(ivAlbumJacket)
        }
    }

    private fun initEvent() {
        with(binding) {
            btnNext.setOnClickListener {
                onDisMiss(tvProgress.text.toString().toInt())
                dismiss()
            }
        }
    }
}