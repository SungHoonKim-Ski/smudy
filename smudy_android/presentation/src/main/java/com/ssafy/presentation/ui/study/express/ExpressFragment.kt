package com.ssafy.presentation.ui.study.express

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentExpressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpressFragment : BaseFragment<FragmentExpressBinding>(
    { FragmentExpressBinding.bind(it)}, R.layout.fragment_express
) {
    private val viewModel:ExpressViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getString("id")
        if (id.isNullOrEmpty()){
            findNavController().popBackStack()
        }
        viewModel.setSongId(id!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        with(binding){
            tvLyricKr.text=""
            loBasic.tvAlbumTitle.text=""
            loBasic.tvAlbumSinger.text=""
        }
    }
}