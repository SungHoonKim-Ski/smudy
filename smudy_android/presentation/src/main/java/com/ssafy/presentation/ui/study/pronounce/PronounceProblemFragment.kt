package com.ssafy.presentation.ui.study.pronounce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.FragmentPronouncePracticeBinding
import com.ssafy.presentation.databinding.FragmentPronounceProblemBinding
import com.ssafy.presentation.ui.study.adapter.PronounceLyricAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PronounceProblemFragment : BaseFragment<FragmentPronounceProblemBinding>(
    { FragmentPronounceProblemBinding.bind(it) }, R.layout.fragment_pronounce_problem
) {
    private val viewModel: PronounceMainViewModel by viewModels({requireParentFragment()})
    private val adapter by lazy { PronounceLyricAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initView()
    }

    private fun initView() {
        with(binding) {
            rvLyrics.adapter = adapter.apply {
                setOnItemClickListener(
                    object : BaseHolder.ItemClickListener {
                        override fun onClick(position: Int) {
                            findNavController().navigate(R.id.action_pronounceProblemFragment_to_pronouncePracticeFragment)
                            viewModel.getTranslateLyric(position)
                        }
                    }
                )
            }
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pronounceProblem.collect {
                    with(binding) {
                        Glide.with(_activity).load(it.albumJacket).into(ivAlbumJacket)
                        tvAlbumSinger.text = it.songName
                        tvAlbumSinger.text = it.songArtist
                        adapter.submitList(it.lyrics)
                    }
                }
            }
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToPractice.collect {
                    findNavController().navigate(R.id.action_pronounceProblemFragment_to_pronouncePracticeFragment)
                }
            }
        }
    }
}