package com.ssafy.presentation.ui.study.pronounce

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentPronouncePracticeBinding
import com.ssafy.presentation.model.PronounceProblem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.util.Locale


@AndroidEntryPoint
class PronouncePracticeFragment : BaseFragment<FragmentPronouncePracticeBinding>(
    { FragmentPronouncePracticeBinding.bind(it) }, R.layout.fragment_pronounce_practice
), TextToSpeech.OnInitListener {
    private val parentViewModel: PronounceProblemViewModel by hiltNavGraphViewModels(R.id.nav_pronounce)
    private var recorder: MediaRecorder? = null
    private var recordPlayer: MediaPlayer? = null
    private var ttsPlayer: MediaPlayer? = null
    private lateinit var recordFile: File
    private lateinit var ttsFile: File
    private lateinit var tts: TextToSpeech

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tts = TextToSpeech(_activity, this)
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
                startRecordPlaying()
            }
            llTts.setOnClickListener {
                startTtsPlaying()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording() {
        recordFile = File(_activity.cacheDir, "recorde.3gp")
        recorder = MediaRecorder(_activity)
            .apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(recordFile)
                prepare()
            }
        recorder!!.start()
        binding.dvRecordDrawing.clearVisualization()
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

    private fun startRecordPlaying() {
        recordPlayer = MediaPlayer()
            .apply {
                setDataSource(recordFile.absolutePath)
                prepare()
            }
        recordPlayer?.setOnCompletionListener {
            stopRecordPlaying()
        }
        recordPlayer?.start()
        binding.dvRecordDrawing.startVisualizing(true)
    }

    private fun stopRecordPlaying() {
        recordPlayer?.release()
        recordPlayer = null
        binding.dvRecordDrawing.stopVisualizing()
    }

    private fun startTtsPlaying() {
        ttsPlayer = MediaPlayer()
            .apply {
                setDataSource(ttsFile.absolutePath)
                prepare()
            }
        ttsPlayer?.setOnCompletionListener {
            stopTtsPlaying()
        }
        ttsPlayer?.start()
        binding.dvTtsDrawing.startVisualizing(true)
    }

    private fun stopTtsPlaying() {
        ttsPlayer?.release()
        ttsPlayer = null
        binding.dvTtsDrawing.stopVisualizing()
    }

    private fun saveTextAsAudioFile() {
        val params = Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "")

        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                // 시작할 때 호출
            }

            override fun onDone(utteranceId: String?) {
                // 파일 생성 완료 후 재생 준비
                readWavFile(ttsFile.absolutePath)
            }

            override fun onError(utteranceId: String?) {

            }

        })

        tts.synthesizeToFile(parentViewModel.translateLyric.value[0], params, ttsFile, "UniqueID")
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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
            ttsFile = File(_activity.cacheDir, "tts_output.wav")
            saveTextAsAudioFile()
        }
    }

    fun readWavFile(filePath: String) {
        val file = File(filePath)
        val inputStream = FileInputStream(file)

        // WAV 헤더 스킵 (44 바이트 일반적)
        val header = ByteArray(44)
        inputStream.read(header)

        // 샘플 레이트 및 데이터 읽기
        val sampleRate = 44100  // 예: 44100Hz
        val bytesPerSample = 2  // 16비트 PCM
        val durationPerChunk = 20 // ms
        val numSamplesPerChunk = (sampleRate / 1000) * durationPerChunk

        val buffer = ByteArray(numSamplesPerChunk * bytesPerSample)
        var bytesRead: Int
        var maxAmplitude: Short = 0

        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            for (i in 0 until bytesRead step 2) {
                val amplitude =
                    ((buffer[i + 1].toInt() shl 8) or (buffer[i].toInt() and 0xff)).toShort()
                if (amplitude > maxAmplitude) maxAmplitude = amplitude
            }
            Log.e("TAG", "readWavFile: $maxAmplitude")
            binding.dvTtsDrawing.onRequestCurrentAmplitude = { maxAmplitude.toInt() }
            binding.dvTtsDrawing.startVisualizing(false)
            maxAmplitude = 0  // reset for the next chunk
        }
        binding.dvTtsDrawing.stopVisualizing()
        inputStream.close()
    }
}