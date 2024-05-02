package com.ssafy.presentation.ui.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentMusicSearchBinding

class MusicSearchFragment : BaseFragment<FragmentMusicSearchBinding>(
    { FragmentMusicSearchBinding.bind(it) }, R.layout.fragment_music_search
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            it.findViewById<BottomNavigationView>(R.id.bn_bar).visibility = View.GONE
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    private var list = arrayOf("abcd", "abca", "abcb", "abcc", "bbaa", "bbab", "bcab", "bdab")
    private val adapter by lazy {
        ArrayAdapter(_activity, android.R.layout.simple_dropdown_item_1line, list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    private fun initView() {
        with(binding) {
            actvKeywordSearch.setAdapter(adapter)
        }
    }

    private fun initEvent() {
        with(binding) {
            actvKeywordSearch.setOnItemClickListener { parent, view, position, id ->
            }
            btnNavigateStudyList.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.let {
            it.findViewById<BottomNavigationView>(R.id.bn_bar).visibility = View.VISIBLE
        }
    }
}