package com.ssafy.presentation.ui.study.express

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentExpressResultBinding
import com.ssafy.presentation.model.Music
import com.ssafy.presentation.model.express.ExpressResult
import com.ssafy.presentation.ui.study.express.adapter.ExpressResultAdapter

class ExpressResultFragment : BaseFragment<FragmentExpressResultBinding>(
    { FragmentExpressResultBinding.bind(it) }, R.layout.fragment_express_result
) {
    private val expressResultAdapter by lazy { ExpressResultAdapter() }
    private lateinit var data: ArrayList<ExpressResult>
    private lateinit var musicInfo: Music
    private var isHistory:Boolean = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelableArrayList("result")!!
            musicInfo = it.getParcelable("song",Music::class.java)!!
            isHistory=it.getBoolean("IsHistory")

        } ?: run {
            Toast.makeText(context, "오류가 발생했습니다. 다시 시도 해주세요.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    private fun initView(){
        with(binding){
            rvExpressResult.adapter = expressResultAdapter
            expressResultAdapter.submitList(data)
            tvAlbumSinger.text=musicInfo.artist
            tvAlbumTitle.text=musicInfo.title
            Glide.with(_activity).load(musicInfo.jacket).into(ivAlbumJacket)
        }
    }

    private fun initEvent() {
        with(binding) {
            btnComplete.setOnClickListener {
                if (isHistory){
                    findNavController().popBackStack()
                } else {
                    findNavController().navigate(R.id.action_expressResultFragment_to_studyFragment)
                }
            }
        }
    }
}