package com.ssafy.presentation.ui.study.fill

import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.ssafy.domain.model.ApiResult
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.base.toMilliSecond
import com.ssafy.presentation.databinding.FragmentFillBinding
import com.ssafy.presentation.model.toQuestion
import com.ssafy.presentation.ui.MainActivityViewModel
import com.ssafy.util.spotify.SpotifyManager
import dagger.Module
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FillFragment"
@AndroidEntryPoint
class FillFragment(
    private val songId: String = "1EzrEOXmMH3G43AXT1y7pA",

) : BaseFragment<FragmentFillBinding>(
    { FragmentFillBinding.bind(it) }, R.layout.fragment_fill
) {

    @Inject
    lateinit var spotifyManager: SpotifyManager

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val fillFragmentViewModel: FillFragmentViewModel by viewModels()

    private val blankLyricAdapter = BlankLyricAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spotifyManager.spotifyAuthenticate(requireActivity())
        registerObserve()
        initView()
    }

    private fun initView(){

        fillFragmentViewModel.setSongId(songId)
        fillFragmentViewModel.getSongWithBlank(songId)


        with(binding){
            rvLyrics.apply {
                adapter = blankLyricAdapter.apply{
                    setOnItemClickListener(object: BaseHolder.ItemClickListener{
                        override fun onClick(position: Int) {
                            val curStartTimeStamp = currentList[position].lyricStartTimeStamp
                            fillFragmentViewModel.setCurTime(
                                curStartTimeStamp
                            )
                            spotifyManager.seekTo(curStartTimeStamp)
                            InputDialog(requireContext(),  currentList[position]).apply {
                                setInputListener(object: InputDialog.InputListener{
                                    override fun onInput(input: String) {
                                        with(fillFragmentViewModel){
                                            setQuestionInput(input, position)
                                            submitList(blankQuestionList)
                                            notifyItemChanged(position)
                                        }
                                    }
                                })
                            }.show(parentFragmentManager, "")
                        }
                    })

                }
            }

            with(fillFragmentViewModel){
                btnPlay.setOnClickListener{
                    if(playerState == PlayState.INIT){
                        spotifyManager.playTrack(songId)
                    }else{
                        spotifyManager.resumeTrack()
                    }
                    it.visibility = View.GONE
                    btnPause.visibility = View.VISIBLE
                    setPlayerState(PlayState.PLAYING)
                    timerStart()
                }
                btnPause.setOnClickListener{
                    stopPlayer()

                }
                btnReplay.setOnClickListener{
                    spotifyManager.seekTo(0)
                    setCurTime(0)
                }

                btnNxt.setOnClickListener{
                    setCurLine(curLine+1)
                    val curTimeStamp = blankQuestionList[curLine].lyricStartTimeStamp
                    spotifyManager.seekTo(curTimeStamp)
                    setCurTime(curTimeStamp)

                }
                btnPrv.setOnClickListener{
                    setCurLine(curLine-1)
                    val curTimeStamp = blankQuestionList[curLine].lyricStartTimeStamp
                    spotifyManager.seekTo(curTimeStamp)
                    setCurTime(curTimeStamp)
                }
                btnSubmit.setOnClickListener {
                    submitAnswer()
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
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
                                with(binding.loBasic){
                                    tvAlbumTitle.text = it.data.songName
                                    tvAlbumSinger.text = it.data.songArtist
                                    Glide.with(requireContext())
                                        .load(it.data.albumJacket)
                                        .into(ivAlbumJacket)
                                }
                                binding.tvNumOfQuestions.text = fillFragmentViewModel.numOfQuestion.toString()
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
                        if(curIdx!=-1){
                            notifyItemChanged(curIdx)
                        }
                    }
                    if(curIdx!=-1){
                        (binding.rvLyrics.layoutManager as LinearLayoutManager)
                            .scrollToPositionWithOffset(curIdx, binding.rvLyrics.height/3)
                        binding.tvProgress.text = "${curIdx+1} / "
                        setCurLine(curIdx)
                    }
                }
            }

        }

    }

    private fun stopPlayer(){
        with(binding){
            spotifyManager.pauseTrack()
            btnPause.visibility = View.GONE
            btnPlay.visibility = View.VISIBLE
            with(fillFragmentViewModel){
                if(playerState == PlayState.PLAYING){
                    setPlayerState(PlayState.PAUSED)
                    timerStop()
                }

            }
        }
    }

    override fun onPause() {
        Log.d(TAG, "onPause: pause")
        stopPlayer()
        super.onPause()
    }

}