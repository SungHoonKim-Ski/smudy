package com.ssafy.presentation.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kakao.sdk.user.model.User
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.user.UserInfo
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentMainBinding
import com.ssafy.presentation.ui.main.adapter.MainMusicAdapter
import com.ssafy.presentation.ui.main.adapter.MainStreakAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "MainFragment"

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(
    { FragmentMainBinding.bind(it) }, R.layout.fragment_main
) {

    private val recentStudyMusicAdapter = MainMusicAdapter(false)
    private val recommendMusicAdapter = MainMusicAdapter()
    private val streakAdapter = MainStreakAdapter()
    private val mainViewModel by viewModels<MainFragmentViewModel>()
    private val inc = 40

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        registerObservers()
    }

    private fun initView() {
        with(binding) {
            rvRecentMusic.apply {
                adapter = recentStudyMusicAdapter
            }
            rvStreak.apply {
                adapter = streakAdapter
            }
            rvRecommendedMusic.apply {
                adapter = recommendMusicAdapter
            }

            cvProfile.setOnClickListener {
//                findNavController().navigate(R.id.historyFragment)
                findNavController().navigate(R.id.fillFragment)
            }

            cvDailySentence.setOnClickListener{
                mainViewModel.setDailyIsEnglish()
            }

            cvWrongSentence.setOnClickListener{
                mainViewModel.setWrongIsEnglish()
            }

        }
        with(mainViewModel) {
            getUserInfo()
            getStreak()
            getDailyLyric()
            getWrongLyric()
            getRecommendedSongs()
        }
    }


    private fun registerObservers() {
        with(mainViewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    userInfo.collect {
                        when (it) {
                            is ApiResult.Success -> { setUserData(it.data) }
                            is ApiResult.Failure -> { Log.d(TAG, "registerObservers: Failure") }
                            is ApiResult.Loading -> { Log.d(TAG, "registerObservers: Loading") }
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    streak.collect {
                        when (it) {
                            is ApiResult.Success -> { streakAdapter.submitList(it.data) }
                            is ApiResult.Failure -> { Log.d(TAG, "registerObservers: Failure") }
                            is ApiResult.Loading -> { Log.d(TAG, "registerObservers: Loading") }
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    dailyLyric.collect {
                        when (it) {
                            is ApiResult.Success -> { setDailyLyric(it.data) }
                            is ApiResult.Failure -> { Log.d(TAG, "registerObservers: Failure") }
                            is ApiResult.Loading -> { Log.d(TAG, "registerObservers: Loading") }
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    wrongLyric.collect {
                        when (it) {
                            is ApiResult.Success -> {
                                setWrongLyric(it.data)
                            }

                            is ApiResult.Failure -> {
                                Log.d(TAG, "registerObservers: Failure")
                            }

                            is ApiResult.Loading -> {
                                Log.d(TAG, "registerObservers: Loading")
                            }
                        }
                    }
                }
            }

            lifecycleScope.launch {
                recommendedSongs.collect {
                    when (it) {
                        is ApiResult.Success -> {
                            recommendMusicAdapter.submitList(it.data)
                        }

                        is ApiResult.Failure -> {
                            Log.d(TAG, "registerObservers: Failure")
                        }

                        is ApiResult.Loading -> {
                            Log.d(TAG, "registerObservers: Loading")
                        }
                    }
                }
            }

            lifecycleScope.launch {
                dailyIsEnglish.observe(viewLifecycleOwner) {
                    binding.todaySentence.text =
                        if (it) dailyLyricValue.value!!.lyricEn
                        else dailyLyricValue.value!!.lyricKo
                }
            }

            lifecycleScope.launch{
                wrongIsEnglish.observe(viewLifecycleOwner){
                    binding.tvRepeatWrong.text =
                        if (it) wrongLyricValue.value!!.lyricEn
                        else wrongLyricValue.value!!.lyricKo
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUserData(data: UserInfo) {
        val (cur, level) = calLevel(data.exp)
        with(binding) {
            tvProfileName.text = data.name
            Glide.with(requireContext()).load(data.img)
                .into(binding.cvProfile)
            pbExp.max = level * inc
            pbExp.progress = cur
            tvCurrentLevel.text = "lv.${level}"
            tvNextLevel.text = "lv.${level + 1}"
        }
        recentStudyMusicAdapter.submitList(data.history)
    }

    private fun calLevel(exp: Int): Pair<Int, Int> {
        var cur = exp
        var level = 1
        while (cur >= inc * level) {
            cur -= inc * level
            level++
        }
        return Pair(cur, level)
    }
}