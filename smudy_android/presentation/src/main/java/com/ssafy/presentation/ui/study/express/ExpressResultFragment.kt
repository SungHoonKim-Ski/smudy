package com.ssafy.presentation.ui.study.express

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentExpressResultBinding
import com.ssafy.presentation.model.express.ExpressResult
import com.ssafy.presentation.ui.study.express.adapter.ExpressResultAdapter

class ExpressResultFragment : BaseFragment<FragmentExpressResultBinding>(
    { FragmentExpressResultBinding.bind(it) }, R.layout.fragment_express_result
) {
    private val expressResultAdapter by lazy { ExpressResultAdapter() }
    private lateinit var data: List<ExpressResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getParcelableArray("result") as List<ExpressResult>
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    private fun initView(){
        expressResultAdapter.submitList(data)
    }

    private fun initEvent() {
        with(binding) {
            btnComplete.setOnClickListener {  }
        }
    }
}