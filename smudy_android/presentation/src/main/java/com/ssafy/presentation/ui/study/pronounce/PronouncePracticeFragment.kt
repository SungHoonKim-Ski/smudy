package com.ssafy.presentation.ui.study.pronounce

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentPronouncePracticeBinding
import com.ssafy.presentation.model.PronounceProblem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class PronouncePracticeFragment : BaseFragment<FragmentPronouncePracticeBinding>(
    { FragmentPronouncePracticeBinding.bind(it) }, R.layout.fragment_pronounce_practice
) {
    private val parentViewModel: PronounceProblemViewModel by hiltNavGraphViewModels(R.id.nav_pronounce)
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer?=null
    private lateinit var file: File

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated: $parentViewModel")
        initView()
        initObserve()
        initEvent()
    }

    private fun initView() {
        with(binding) {
            dvRecordDrawing.onRequestCurrentAmplitude = { recorder?.maxAmplitude ?: 0 }
        }
    }

    private fun changeVisibility() {
        with(binding) {
            ivRecordStop.isVisible = !ivRecordStop.isVisible
            ivRecordStart.isVisible = !ivRecordStart.isVisible
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initEvent() {
        with(binding) {
            ivRecordStart.setOnClickListener {
                startRecording()
                changeVisibility()
            }
            ivRecordStop.setOnClickListener {
                stopRecording()
                changeVisibility()
            }
            btnResetRecord.setOnClickListener {
                startPlaying()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording() {
        file = File(_activity.cacheDir, "recorde.3gp")
        recorder = MediaRecorder(_activity)
            .apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(file)
                prepare()
            }
        recorder!!.start()
        binding.dvRecordDrawing.startVisualizing(false)
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
        binding.dvRecordDrawing.stopVisualizing()
    }

    private fun startPlaying() {
        player = MediaPlayer()
            .apply {
                setDataSource(file.absolutePath)
                prepare()
            }
        player?.setOnCompletionListener {
            stopPlaying()
        }
        player?.start()
        binding.dvRecordDrawing.startVisualizing(true)
    }

    private fun stopPlaying() {
        player?.release()
        player = null
        binding.dvRecordDrawing.stopVisualizing()
    }

    private fun setMusicView(pronounce: PronounceProblem) {
        with(binding) {
            tvAlbumTitle.text = pronounce.songName
            tvAlbumSinger.text = pronounce.songArtist
            Glide.with(_activity).load(pronounce.albumJacket).into(ivAlbumJacket)
        }
    }

    private fun setLyricView(lyrics: List<String>) {
        with(binding) {
            tvLyric.text = lyrics[0]
            tvTranslatedLyric.text = lyrics[1]
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            parentViewModel.pronounceProblem.collectLatest {
                setMusicView(it)
            }
            parentViewModel.translateLyric.collectLatest {
                setLyricView(it)
            }
        }
    }
}