package com.ssafy.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.domain.model.user.Streak
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemStreakBinding

class MainStreakAdapter: BaseAdapter<Streak>() {

    class StreakHolder(private val binding: ItemStreakBinding) : BaseHolder<Streak>(binding){
        override fun bindInfo(data: Streak) {
            Glide.with(binding.root)
                .load(data.albumJacket)
                .into(binding.ivAlbumJacket)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Streak> {
        return StreakHolder(ItemStreakBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}