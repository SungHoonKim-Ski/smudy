package com.ssafy.presentation.ui.study.fill

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.domain.model.ApiResult
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentFillBinding
import com.ssafy.presentation.ui.MainActivityViewModel
import com.ssafy.util.spotify.SpotifyManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FillFragment"
@AndroidEntryPoint
class FillFragment : BaseFragment<FragmentFillBinding>(
    { FragmentFillBinding.bind(it) }, R.layout.fragment_fill
) {

    @Inject
    lateinit var spotifyManager: SpotifyManager

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val fillFragmentViewModel: FillFragmentViewModel by viewModels()

    private val blankLyricAdapter = BlankLyricAdapter()

    private val songId = "1EzrEOXmMH3G43AXT1y7pA"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spotifyManager.spotifyAuthenticate(requireActivity())
        registerObserve()
        initView()
    }

    private fun initView(){
        fillFragmentViewModel.getSongWithBlank(songId)

        with(binding){
            rvLyrics.apply {
                adapter = blankLyricAdapter.apply{
                    setPasser(object: BlankLyricAdapter.BlankLyricHolder.InputPasser{
                        override fun onPass(input: String, position: Int) {
                            fillFragmentViewModel.setQuestionInput(input, position)
                        }

                        override fun onGetInput(position: Int)
                         = fillFragmentViewModel.getQuestionInput(position)
                    })
                }
            }

            with(fillFragmentViewModel){
                btnPlay.setOnClickListener{
                    spotifyManager.playTrack(songId)
                    setCurTime(0)
                    timerStart()
                }
                btnPause.setOnClickListener{
                    spotifyManager.pauseTrack()
                    timerStop()
                }
                btnResume.setOnClickListener{
                    spotifyManager.resumeTrack()
                    timerStart()
                }
            }

        }

    }

    private fun registerObserve(){
        with(mainActivityViewModel){
            spotifyActivityResult.observe(viewLifecycleOwner){
                spotifyManager.onActivityResult(it.first, it.second, it.third)
            }
        }

        with(fillFragmentViewModel){
            lifecycleScope.launch{
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    songResult.collect{
                        when(it){
                            is ApiResult.Success->{
                                blankLyricAdapter.submitList(blankQuestionList)
                            }
                            is ApiResult.Failure->{
                                Log.d(TAG, "registerObservers: Failure")
                            }
                            is ApiResult.Loading->{
                                Log.d(TAG, "registerObservers: Loading")
                            }
                        }
                    }
                }
            }

            curTime.observe(viewLifecycleOwner){
                if(timeLineIndexMap.contains(it)){
                    val curIdx = timeLineIndexMap[it]!!
                    val oldIdx = blankLyricAdapter.curPosition
                    blankLyricAdapter.apply {
                        setCurPosition(curIdx)
                        notifyItemChanged(oldIdx)
                        notifyItemChanged(curIdx)
                    }
                    (binding.rvLyrics.layoutManager as LinearLayoutManager)
                        .scrollToPositionWithOffset(curIdx, binding.rvLyrics.height/2)
                }
            }

        }

    }

}