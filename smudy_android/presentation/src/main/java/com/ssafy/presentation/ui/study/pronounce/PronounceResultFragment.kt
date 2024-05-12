package com.ssafy.presentation.ui.study.pronounce

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentPronounceResultBinding
import com.ssafy.presentation.model.Music
import com.ssafy.presentation.model.pronounce.GradedPronounce
import com.ssafy.presentation.ui.study.pronounce.dialog.PronouncePitchDialog


class PronounceResultFragment : BaseFragment<FragmentPronounceResultBinding>(
    { FragmentPronounceResultBinding.bind(it) }, R.layout.fragment_pronounce_result
) {
    private lateinit var pronounceResult: GradedPronounce
    private lateinit var music: Music
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argument1 = arguments?.getParcelable<GradedPronounce>("pronounceResult")
        val argument2 = arguments?.getParcelable<Music>("song")
        if (argument1 == null || argument2 == null) {
            findNavController().popBackStack()
        }
        pronounceResult = argument1!!
        music = argument2!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated: $pronounceResult")
        initView()
        initEvent()
    }

    private fun initView() {
        with(binding) {
            tvLyric.text = pronounceResult.lyricSentenceEn
            tvTranslatedLyric.text = pronounceResult.ttsPronounce
            tvUserPronounce.text = pronounceResult.lyricSentenceKo
            tvAlbumTitle.text = music.title
            tvAlbumSinger.text = music.artist
            Glide.with(_activity).load(music.jacket).into(ivAlbumJacket)
        }
    }

    private fun initEvent() {
        with(binding){
            cvPitchResult.setOnClickListener {
                pronounceResult.lyricAiAnalyze.apply {
                    PronouncePitchDialog(this.refPitchData,this.testPitchData,this.refTimestamps,_activity).show(parentFragmentManager,"PitchResult")
                }
            }
            cvIntensityResult.setOnClickListener {  }
            cvFormantResult.setOnClickListener {  }
        }
    }
}