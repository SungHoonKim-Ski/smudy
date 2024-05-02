package com.ssafy.presentation.ui.study

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentMusicSearchBinding
import com.ssafy.presentation.ui.study.adapter.NoFilterArrayAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MusicSearchFragment : BaseFragment<FragmentMusicSearchBinding>(
    { FragmentMusicSearchBinding.bind(it) }, R.layout.fragment_music_search
) {
    private val viewModel by viewModels<MusicSearchViewModel>()
    private val keywordsAdapter by lazy {
        NoFilterArrayAdapter<String>(
            _activity,
            android.R.layout.simple_list_item_1,
            mutableListOf()
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        initObserve()
    }

    private fun initView() {
        activity?.findViewById<BottomNavigationView>(R.id.bn_bar)?.visibility = View.GONE
        with(binding) {
            actvKeywordSearch.setAdapter(keywordsAdapter)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEvent() {
        with(binding) {
            btnNavigateStudyList.setOnClickListener {
                // 저장하는 통신
            }
            actvKeywordSearch.apply {
                setOnItemClickListener { _, _, position, _ ->
                    // 검색어로 서버 통신
                    hideKeyboard()
                    this.clearFocus()
                }
                setOnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_DOWN){
                        this.showDropDown()
                    }
                    false
                }
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.isNotEmpty()){
                            viewModel.searchKeyword(s.toString())
                        }else{
                            keywordsAdapter.clear()
                            keywordsAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }

                })
            }
        }
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.keywords.collect {
                keywordsAdapter.clear()
                keywordsAdapter.addAll(it)
                keywordsAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.findViewById<BottomNavigationView>(R.id.bn_bar)?.visibility = View.VISIBLE
    }
}