package com.ssafy.presentation.ui.study.express

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentExpressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExpressFragment : BaseFragment<FragmentExpressBinding>(
    { FragmentExpressBinding.bind(it) }, R.layout.fragment_express
) {
    private val viewModel: ExpressViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getString("id")
        if (id.isNullOrEmpty()) {
            findNavController().popBackStack()
        }
        viewModel.setSongId(id!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initEvent()
    }

    private fun initObserve() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.album.collect {
                    loBasic.tvAlbumSinger.text = it.artist
                    loBasic.tvAlbumTitle.text = it.title
                    Glide.with(_activity).load(it.jacket).into(loBasic.ivAlbumJacket)
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.currentProblemIndex.collect {
                    tvProgress.text = ("${it + 1} ")
                    tvLyricKr.text = viewModel.getCurrentExpressProblem(it)
                }
            }
        }
    }

    private fun initEvent(){
        with(binding){
            btnConfirm.setOnClickListener { viewModel.checkExpressProblem() }
        }
    }
}