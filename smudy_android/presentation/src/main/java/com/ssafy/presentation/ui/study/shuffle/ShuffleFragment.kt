package com.ssafy.presentation.ui.study.shuffle

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.ssafy.domain.model.ApiResult
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.FragmentShuffleBinding
import com.ssafy.presentation.model.ParcelableShuffleSubmitResult
import com.ssafy.presentation.model.toParcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "ShuffleFragment"

@AndroidEntryPoint
class ShuffleFragment : BaseFragment<FragmentShuffleBinding>(
    { FragmentShuffleBinding.bind(it) }, R.layout.fragment_shuffle
) {

    private val shuffleViewModel: ShuffleViewModel by viewModels()
    private val selectedAdapter = ShuffleAdapter()
    private val candidAdapter = ShuffleAdapter()
    private lateinit var songId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserve()
        initView()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {
        songId = requireArguments().getString("id")!!

        with(binding) {

            tvNumOfQuestions.text = "5"

            rvSelected.apply {
                adapter = selectedAdapter.apply {
                    setOnItemClickListener(object : BaseHolder.ItemClickListener {
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onClick(position: Int) {
                            with(shuffleViewModel) {
                                val destIdx = currentList[position].origPosition
                                selectedList[curIdx.value!!].removeAt(position)
                                selectedAdapter.submitList(selectedList[curIdx.value!!])
                                inputWords[curIdx.value!!][position] = ""
                                selectedAdapter.notifyDataSetChanged()
                                shuffledList[curIdx.value!!][destIdx] =
                                    shuffledList[curIdx.value!!][destIdx].copy(isVisible = true)
                                candidAdapter.submitList(shuffledList[curIdx.value!!])
                                candidAdapter.notifyItemChanged(destIdx)
                            }
                        }

                    })
                }
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.CENTER
                    maxLine = 4
                }

                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    ).apply {
                        setDrawable(
                            requireContext().resources.getDrawable(
                                R.color.black,
                                context.theme
                            )
                        )
                    })
            }

            rvShuffle.apply {
                adapter = candidAdapter.apply {
                    setOnItemClickListener(object : BaseHolder.ItemClickListener {
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onClick(position: Int) {
                            with(shuffleViewModel) {
                                val destIdx = selectedList[curIdx.value!!].size - 20
                                selectedList[curIdx.value!!].add(
                                    destIdx,
                                    currentList[position].copy()
                                )
                                selectedAdapter.submitList(selectedList[curIdx.value!!])
                                selectedAdapter.notifyDataSetChanged()
                                shuffledList[curIdx.value!!][position] =
                                    shuffledList[curIdx.value!!][position].copy(isVisible = false)
                                candidAdapter.submitList(shuffledList[curIdx.value!!])
                                inputWords[curIdx.value!!][destIdx] =
                                    selectedList[curIdx.value!!][destIdx].word
                            }

                        }
                    })
                }
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.CENTER
                    maxLine = 4
                }

            }

        }

        with(shuffleViewModel) {
            getShuffle(songId!!)
            with(binding) {
                btnNxt.setOnClickListener {
                    Log.d(TAG, "initView: ${curIdx.value!! + 1}")
                    setCurIdx(curIdx.value!! + 1)
                }
                btnPrv.setOnClickListener { setCurIdx(curIdx.value!! - 1) }
                btnConfirm.setOnClickListener { submitShuffle(songId!!) }
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun registerObserve() {
        with(shuffleViewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    shuffleResult.collect {

                        if (it is ApiResult.Success) {
                            val data = it.data
                            setCurIdx(0)

                            with(binding.loBasic) {
                                tvAlbumTitle.text = data.songName
                                tvAlbumSinger.text = data.songArtist
                                Glide.with(requireContext())
                                    .load(data.albumJacket)
                                    .into(ivAlbumJacket)
                            }
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    shuffleSubmitResult.collect {
                        if (it is ApiResult.Success) {
                            val data = (shuffleResult.value as ApiResult.Success).data

                            findNavController().navigate(
                                ShuffleFragmentDirections.actionShuffleFragmentToShuffleResultFragment(
                                    ParcelableShuffleSubmitResult(
                                        data.songName,
                                        data.songArtist,
                                        data.albumJacket,
                                        it.data.totalSize,
                                        it.data.score,
                                        it.data.correct.map { p -> p.toParcelable() },
                                        it.data.wrong.map { p -> p.toParcelable() }
                                    )
                                )
                            )
                        }
                    }
                }
            }

            curIdx.observe(viewLifecycleOwner) {
                Log.d(TAG, "registerObserve: $it")
                if (it > -1) {
                    binding.tvProgress.text = "${it + 1} /"
                    candidAdapter.submitList(
                        shuffledList[it]
                    )
                    binding.tvLyricKr.text = koreanList[it]
                    selectedAdapter.submitList(selectedList[it])
                    if (it >= 4) {
                        binding.btnNxt.isEnabled = false
                        binding.btnConfirm.isEnabled = true
                    } else {
                        binding.btnConfirm.isEnabled = false
                        if (it <= 0) {
                            binding.btnPrv.isEnabled = false
                        } else {
                            binding.btnNxt.isEnabled = true
                            binding.btnPrv.isEnabled = true
                        }
                    }
                }

            }
        }
    }


}