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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentPronouncePracticeBinding
import com.ssafy.presentation.model.Music
import com.ssafy.presentation.model.PronounceProblem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.Locale

@AndroidEntryPoint
class PronouncePracticeFragment : BaseFragment<FragmentPronouncePracticeBinding>(
    { FragmentPronouncePracticeBinding.bind(it) }, R.layout.fragment_pronounce_practice
), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private val parentViewModel: PronounceProblemViewModel by hiltNavGraphViewModels(R.id.nav_pronounce)
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private lateinit var file: File
    private lateinit var ttsFile: File

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
            tvLyric.text = parentViewModel.translateLyric[0]
            tvTranslatedLyric.text = parentViewModel.translateLyric[1]
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bn_bar)?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        activity?.findViewById<BottomNavigationView>(R.id.bn_bar)?.visibility = View.VISIBLE
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
            lvTtsSmudy.setOnClickListener {
                startTtsPlaying()
            }
            btnGradePronounce.setOnClickListener {
                parentViewModel.gradePronounceProblem(file, ttsFile)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording() {
        binding.dvRecordDrawing.clearVisualization()
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

    private fun startTtsPlaying() {
        player = MediaPlayer()
            .apply {
                setDataSource(ttsFile.absolutePath)
                prepare()
            }
        player?.setOnCompletionListener {
            stopTtsPlaying()
        }
        player?.start()
        binding.dvTtsDrawing.startVisualizing(true)
    }

    private fun stopTtsPlaying() {
        player?.release()
        player = null
        binding.dvTtsDrawing.stopVisualizing()
    }

    private fun setMusicView(pronounce: PronounceProblem) {
        with(binding) {
            tvAlbumTitle.text = pronounce.songName
            tvAlbumSinger.text = pronounce.songArtist
            Glide.with(_activity).load(pronounce.albumJacket).into(ivAlbumJacket)
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            parentViewModel.pronounceProblem.collectLatest {
                setMusicView(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            parentViewModel.navigationTrigger.collect {
                if (it) {
                    val bundle = Bundle().apply {
                        putParcelable("pronounceResult", parentViewModel.pronounceResult)
                        putParcelable(
                            "song",
                            Music(
                                parentViewModel.pronounceProblem.value.songName,
                                parentViewModel.pronounceProblem.value.songArtist,
                                parentViewModel.pronounceProblem.value.albumJacket
                            )
                        )
                    }
                    findNavController().navigate(
                        R.id.action_pronouncePracticeFragment_to_pronounceResultFragment,
                        bundle
                    )
                }
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
            ttsFile = File(_activity.cacheDir, "tts.wav")
            // 음성 파일로 텍스트를 저장
            saveTextAsAudioFile()
        }
    }

    private fun saveTextAsAudioFile() {
        val params = Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "")

        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}

            override fun onDone(utteranceId: String?) {
                // 파일 생성 완료 후 재생 준비
                readWavFile()
            }

            override fun onError(utteranceId: String?) {
                Log.e("TAG", "onError: $utteranceId")
            }
        })

        tts.synthesizeToFile(parentViewModel.translateLyric[0], params, ttsFile, "UniqueID")
    }

    fun readWavFile() {
        CoroutineScope(Dispatchers.IO).launch {
            val inputStream = FileInputStream(ttsFile)

            // WAV 헤더 읽기
            val header = ByteArray(44)
            inputStream.read(header)

            // 샘플 레이트 및 데이터 읽기
            val sampleRate = ByteBuffer.wrap(header, 24, 4).order(ByteOrder.LITTLE_ENDIAN).int
            val bitsPerSample =
                ByteBuffer.wrap(header, 34, 2).order(ByteOrder.LITTLE_ENDIAN).short.toInt()
            val bytesPerSample = bitsPerSample / 8
            val durationPerChunk = 20 // ms
            val numSamplesPerChunk = (sampleRate / 1000) * durationPerChunk

            val reduceBytes = numSamplesPerChunk * bytesPerSample * 20

            // `data` 청크 크기 읽기
            val dataSize =
                ByteBuffer.wrap(header, 40, 4).order(ByteOrder.LITTLE_ENDIAN).int - reduceBytes

            val buffer = ByteArray(numSamplesPerChunk * bytesPerSample)
            var bytesRead = 0
            var maxAmplitude: Short = 0
            var totalBytesRead = 0

            while (totalBytesRead < dataSize && inputStream.read(buffer)
                    .also { bytesRead = it } != -1
            ) {
                for (i in 0 until bytesRead step 2) {
                    val amplitude =
                        ((buffer[i + 1].toInt() shl 8) or (buffer[i].toInt() and 0xff)).toShort()
                    if (amplitude > maxAmplitude) maxAmplitude = amplitude
                }
                withContext(Dispatchers.Main) {
                    binding.dvTtsDrawing.addAmplitude(maxAmplitude.toInt())
                }
                maxAmplitude = 0  // reset for the next chunk
                totalBytesRead += bytesRead
            }

            inputStream.close()
        }
    }
}
