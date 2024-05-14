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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "ShuffleFragment"

@AndroidEntryPoint
class ShuffleFragment : BaseFragment<FragmentShuffleBinding>(
    { FragmentShuffleBinding.bind(it)}, R.layout.fragment_shuffle
){

    private val shuffleViewModel: ShuffleViewModel by viewModels()
    private val selectedAdapter = ShuffleAdapter()
    private val candidAdapter = ShuffleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserve()
        initView()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView(){
        with(binding){
            rvSelected.apply{
                adapter = selectedAdapter
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.CENTER
                    maxLine = 4
                }

                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply{
                    setDrawable(requireContext().resources.getDrawable(R.color.black, context.theme))
                })
            }

            rvShuffle.apply{
                adapter = candidAdapter.apply{
                    setOnItemClickListener(object: BaseHolder.ItemClickListener{
                        override fun onClick(position: Int) {
                            with(shuffleViewModel){
                                val curList = selectedList[curIdx]
                                val curIdx = curList.size-20
                                curList.add( curIdx, currentList[position])
                                selectedAdapter.submitList(curList)
                                selectedAdapter.notifyItemInserted(curIdx)
                                Log.d(TAG, "onClick: ${selectedAdapter.currentList}")
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

        with(shuffleViewModel){
            getShuffle("1EzrEOXmMH3G43AXT1y7pA")
        }
    }

    private fun registerObserve(){
        with(shuffleViewModel){
            lifecycleScope.launch{
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    shuffleResult.collect{

                        if( it is ApiResult.Success){
                            val data = it.data

                            setCurIdx(0)
                            candidAdapter.submitList(
                                shuffledList[curIdx]
                            )
                            binding.tvLyricKr.text = koreanList[curIdx]
                            selectedAdapter.submitList(selectedList[curIdx])

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
        }
    }


}