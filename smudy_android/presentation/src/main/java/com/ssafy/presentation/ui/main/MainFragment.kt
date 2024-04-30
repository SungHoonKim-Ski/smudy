package com.ssafy.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.domain.model.ApiResult
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        registerObservers()
    }

    private fun initView(){
        with(binding){
            Glide.with(requireActivity())
                .load(R.drawable.ic_bottom_library_music_normal)
                .into(cvProfile)
            tvProfileName.text = "test"

            rvRecentMusic.apply {
                adapter = recentStudyMusicAdapter
                setHasFixedSize(true)
            }

            rvStreak.apply {
                adapter = streakAdapter
                setHasFixedSize(true)
            }
            rvRecommendedMusic.apply {
                adapter = recommendMusicAdapter
            }

            cvProfile.setOnClickListener {
                findNavController().navigate(R.id.historyFragment)
            }
        }
    }

    private fun registerObservers() {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.userInfo.collect{
                    when(it){
                        is ApiResult.Success -> {
                            binding.tvProfileName.text = it.data.name
                            Glide.with(requireContext()).
                                load(it.data.img)
                               .into(binding.cvProfile)
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

    }
}