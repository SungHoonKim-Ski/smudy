package com.ssafy.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemStreakBinding

class MainStreakAdapter: BaseAdapter<String>() {

    class StreakHolder(private val binding: ItemStreakBinding) : BaseHolder<String>(binding){
        override fun bindInfo(data: String) {
            Glide.with(binding.root)
                .load(data)
                .into(binding.ivAlbumJacket)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<String> {
        return StreakHolder(ItemStreakBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}