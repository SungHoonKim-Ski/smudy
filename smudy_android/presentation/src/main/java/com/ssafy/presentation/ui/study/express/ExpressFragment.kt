package com.ssafy.presentation.ui.study.express

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
    private lateinit var backPressedCallback: OnBackPressedCallback
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getString("id")
        if (id.isNullOrEmpty()) {
            findNavController().popBackStack()
        }
        viewModel.setSongId(id!!)
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
                            val bundle = Bundle().apply {
                                putParcelableArrayList("result", viewModel.getExpressProblem())
                                putParcelable("song", viewModel.getAlbumInfo())
                                putBoolean("IsHistory", false)
                            }
                            findNavController().navigate(
                                R.id.action_expressFragment_to_expressResultFragment,
                                bundle
                            )
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
                viewModel.checkExpressProblem(answer)
            }
        }
    }

    private fun showExitConfirmationDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("표현 문제 풀이를 종료 하시겠습니까?")
            .setPositiveButton("종료") { dialog, _ ->
                dialog.dismiss()
                // Handle the positive button click, e.g., exit the fragment
                findNavController().popBackStack()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
                // Handle the negative button click, e.g., do nothing
            }.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.dark_red))
        }
        dialog.show()
    }
}