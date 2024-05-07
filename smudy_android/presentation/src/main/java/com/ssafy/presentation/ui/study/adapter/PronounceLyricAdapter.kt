package com.ssafy.presentation.ui.study.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemLyricOneSentenceBinding

class PronounceLyricAdapter : BaseAdapter<String>() {
    class PronounceHolder(private val binding: ItemLyricOneSentenceBinding) :
        BaseHolder<String>(binding) {
        override fun bindInfo(data: String) {
            with(binding) {
                tvLyric.text = data
                tvLyric.setOnClickListener {
                    clickListener.onClick(layoutPosition)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<String> =
        PronounceHolder(
            ItemLyricOneSentenceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply { setOnItemClickListener(clickListener) }
}