package com.ssafy.presentation.ui.study

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentMusicSearchBinding
import com.ssafy.presentation.ui.study.adapter.MusicRecyclerAdapter
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
    private val musicsAdapter by lazy {
        MusicRecyclerAdapter { data, isChecked ->
            if (isChecked) viewModel.addMusic(data) else viewModel.deleteMusic(data)
        }
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
            rvSearchedMusic.adapter = musicsAdapter
        }
        getMusicList("")
    }

    private fun initEvent() {
        with(binding) {
            btnNavigateStudyList.setOnClickListener {
                // 저장하는 통신
                viewModel.addMusicList()
            }
            actvKeywordSearch.apply {
                // dropdown 아이템 눌렀을 때 해당 텍스트로 검색 통신
                setOnItemClickListener { _, _, position, _ ->
                    // 검색어로 서버 통신
                    getMusicList(viewModel.keywords.value[position])
                    hideKeyboard()
                    clearFocus()
                }
                // 아이템 선택등의 이유로 focus가 없어졌다가 생길때 dropdown 보여주기
                setOnFocusChangeListener { _, isFocus ->
                    if (isFocus) {
                        showDropDown()
                    }
                }
                // 키보드를 통해 검색을 했을 때 동작 방신 구현
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        getMusicList(text.toString())
                        hideKeyboard()
                        clearFocus()
                        dismissDropDown()
                        true
                    } else {
                        false
                    }
                }
                // 글자가 변경되면 추천 검색어를 서버 통신을 통해 가져옴
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
                        if (s.isNotEmpty()) {
                            viewModel.searchKeyword(s.toString())
                        } else {
                            viewModel.deleteKeywords()
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

    private fun getMusicList(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMusicList(query).collect {
                musicsAdapter.submitData(it)
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isAddSuccess.collect{
                if (it.isNotBlank()){
                    Toast.makeText(_activity,it,Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.findViewById<BottomNavigationView>(R.id.bn_bar)?.visibility = View.VISIBLE
    }
}