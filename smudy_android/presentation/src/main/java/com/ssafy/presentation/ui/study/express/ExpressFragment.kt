package com.ssafy.presentation.ui.study.express

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentExpressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExpressFragment : BaseFragment<FragmentExpressBinding>(
    { FragmentExpressBinding.bind(it) }, R.layout.fragment_express
) {
    private val viewModel: ExpressViewModel by viewModels()
    private val args:ExpressFragmentArgs by navArgs()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationDialog("표현 문제 풀이를 종료하시겠습니까?")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = args.Id
        viewModel.setSongId(id)
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
        initObserve()
        initEvent()
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }

    private fun initObserve() {
        with(binding) {
            lifecycleScope.launch {
                viewModel.isLoading.collect{
                    if (it) showLoading() else hideLoading()
                }
            }
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
                    etAnswerSentence.setText("")
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.navigationTrigger.collect {
                    when (it) {
                        "show_dialog" -> {
                            ExpressResultDialog(
                                viewModel.getCurrentExpressResult(),
                                viewModel.getCurrentCount(),
                                viewModel.getAlbumInfo(),
                                _activity
                            ) { onDismissDialog(it) }.show(
                                parentFragmentManager,
                                "ExpressResult"
                            )
                        }

                        "result_screen" -> {
                            findNavController().navigate(ExpressFragmentDirections.actionExpressFragmentToExpressResultFragment(viewModel.getAlbumInfo(),viewModel.getExpressProblem().toTypedArray()))
                        }
                    }
                }
            }
        }
    }

    private fun onDismissDialog(count: Int) {
        viewModel.isComplete(count)
    }

    private fun initEvent() {
        with(binding) {
            btnConfirm.setOnClickListener {
                val answer = etAnswerSentence.text.toString()
                if (answer.isNotBlank()){
                    viewModel.checkExpressProblem(answer)
                    etAnswerSentence.clearFocus()
                } else {
                    Toast.makeText(_activity,"답변을 입력해주세요.",Toast.LENGTH_SHORT).show()
                }
            }
            etAnswerSentence.setOnEditorActionListener { v, actionId, event ->
                Log.e("TAG", "initEvent: $actionId")
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    hideKeyboard()
                    etAnswerSentence.clearFocus()
                    true
                } else {
                    false
                }
            }
        }
    }
}