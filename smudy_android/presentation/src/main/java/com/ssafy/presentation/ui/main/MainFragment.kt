package com.ssafy.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentMainBinding
import com.ssafy.presentation.ui.main.adapter.MainMusicAdapter
import com.ssafy.presentation.ui.main.adapter.MainStreakAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(
    { FragmentMainBinding.bind(it) }, R.layout.fragment_main
) {

    private val recentStudyMusicAdapter = MainMusicAdapter(false)
    private val recommendMusicAdapter = MainMusicAdapter()
    private val streakAdapter = MainStreakAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
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
}