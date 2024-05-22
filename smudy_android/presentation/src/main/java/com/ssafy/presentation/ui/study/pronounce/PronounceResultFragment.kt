package com.ssafy.presentation.ui.study.pronounce

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentPronounceResultBinding
import com.ssafy.presentation.model.Music
import com.ssafy.presentation.model.pronounce.GradedPronounce
import com.ssafy.presentation.ui.study.pronounce.dialog.PronounceFormantDialog
import com.ssafy.presentation.ui.study.pronounce.dialog.PronounceIntensityDialog
import com.ssafy.presentation.ui.study.pronounce.dialog.PronouncePitchDialog


class PronounceResultFragment : BaseFragment<FragmentPronounceResultBinding>(
    { FragmentPronounceResultBinding.bind(it) }, R.layout.fragment_pronounce_result
) {
    private val args:PronounceResultFragmentArgs by navArgs()
    private lateinit var pronounceResult: GradedPronounce
    private lateinit var music: Music
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pronounceResult = args.PronounceResult
        music = args.Music
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bn_bar)?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        activity?.findViewById<BottomNavigationView>(R.id.bn_bar)?.visibility = View.VISIBLE
    }

    private fun initView() {
        with(binding) {
            tvLyric.text = pronounceResult.lyricSentenceEn
            tvTranslatedLyric.text = pronounceResult.lyricSentenceKo
            tvUserPronounce.text = pronounceResult.userLyricSttEn
            tvAlbumTitle.text = music.title
            tvAlbumSinger.text = music.artist
            Glide.with(_activity).load(music.jacket).into(ivAlbumJacket)
            if (args.IsHistory){
                btnNavigatePractice.visibility = View.GONE
            }
        }
    }

    private fun initEvent() {
        with(binding) {
            cvPitchResult.setOnClickListener {
                pronounceResult.lyricAiAnalyze.apply {
                    PronouncePitchDialog(
                        refPitchData,
                        testPitchData,
                        refTimestamps,
                        _activity
                    ).show(parentFragmentManager, "PitchResult")
                }
            }
            cvIntensityResult.setOnClickListener {
                pronounceResult.lyricAiAnalyze.apply {
                    PronounceIntensityDialog(
                        refIntensityData,
                        testIntensityData,
                        refTimestamps,
                        _activity
                    ).show(parentFragmentManager, "IntensityResult")
                }
            }
            cvFormantResult.setOnClickListener {
                pronounceResult.lyricAiAnalyze.apply {
                    PronounceFormantDialog(
                        refFormantsAvg,
                        testFormantsAvg,
                        _activity
                    ).show(parentFragmentManager, "PronounceFormant")
                }
            }
            btnNavigatePractice.setOnClickListener { findNavController().popBackStack() }
            btnComplete.setOnClickListener {
                if (args.IsHistory){
                    findNavController().popBackStack()
                }else {
                    findNavController().popBackStack(R.id.pronounceProblemFragment, false)
                }
            }
        }
    }
}