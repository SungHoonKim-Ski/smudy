package com.ssafy.presentation.ui.study.pronounce

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.presentation.NavPronounceArgs
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
    private val args:NavPronounceArgs by navArgs()
    private lateinit var id: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationDialog("발음 연습을 종료하시겠습니까?")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = args.Id
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPronounceProblem(id)
        viewModel.setSongId(id)
        initObserve()
        initView()
        initEvent()
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pronounceProblem.collect {
                with(binding) {
                    Glide.with(_activity).load(it.albumJacket).into(ivAlbumJacket)
                    tvAlbumSinger.text = it.songName
                    tvAlbumSinger.text = it.songArtist
                    adapter.submitList(it.lyrics)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationTrigger.collect {
                if (it) {
                    findNavController().navigate(R.id.action_pronounceProblemFragment_to_pronouncePracticeFragment)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.isLoading.collect{
                if (it) showLoading() else hideLoading()
            }
        }
    }

    private fun initView() {
        with(binding) {
            activity?.findViewById<BottomNavigationView>(R.id.bn_bar)?.visibility = View.GONE
            rvLyrics.adapter = adapter
        }
    }

    private fun initEvent() {
        adapter.apply {
            setOnItemClickListener(
                object : BaseHolder.ItemClickListener {
                    override fun onClick(position: Int) {
                        viewModel.getTranslateLyric(position)
                    }
                }
            )
        }
        with(binding){
            btnComplete.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.findViewById<BottomNavigationView>(R.id.bn_bar)?.visibility = View.VISIBLE
    }
}