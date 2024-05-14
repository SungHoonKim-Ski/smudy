package com.ssafy.presentation.ui.study.shuffle

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemShuffleBinding
import com.ssafy.presentation.model.ShuffleWord

private const val TAG = "ShuffleAdapter"
class ShuffleAdapter : BaseAdapter<ShuffleWord>() {

    class WordHolder(private val binding: ItemShuffleBinding) : BaseHolder<ShuffleWord>(binding) {
        @SuppressLint("UseCompatLoadingForDrawables")
        override fun bindInfo(data: ShuffleWord) {
            with(binding){
                tvWord.text = data.word
                if (data.word.isBlank() || !data.isVisible) {
                    binding.root.visibility = View.INVISIBLE
                }else{
                    binding.root.visibility = View.VISIBLE
                }
                binding.root.setOnClickListener{
                    clickListener.onClick(layoutPosition)
                    it.visibility = View.INVISIBLE
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ShuffleWord> {
        return WordHolder(
            ItemShuffleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            setOnItemClickListener(clickListener)
        }
    }
}