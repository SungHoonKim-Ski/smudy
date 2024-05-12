package com.ssafy.presentation.ui.study.pronounce

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.FragmentPronounceProblemBinding
import com.ssafy.presentation.ui.study.adapter.PronounceLyricAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PronounceProblemFragment : BaseFragment<FragmentPronounceProblemBinding>(
    { FragmentPronounceProblemBinding.bind(it) }, R.layout.fragment_pronounce_problem
) {
    private val viewModel: PronounceProblemViewModel by hiltNavGraphViewModels(R.id.nav_pronounce)
    private val adapter by lazy { PronounceLyricAdapter() }
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString("id", "")!!
        if (id == "") {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPronounceProblem(id)
        initObserve()
        initView()
        initEvent()
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
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.translateLyric.collect{

                }
            }
        }
    }

    private fun initView() {
        with(binding) {
            rvLyrics.adapter = adapter
        }
    }

    private fun initEvent(){
        adapter.apply {
            setOnItemClickListener(
                object : BaseHolder.ItemClickListener {
                    override fun onClick(position: Int) {
                        viewModel.getTranslateLyric(position)
                        findNavController().navigate(R.id.action_pronounceProblemFragment_to_pronouncePracticeFragment)
                    }
                }
            )
        }
    }
}