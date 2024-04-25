package com.ssafy.presentation.ui.study.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemMusicListButtonHorizontalBinding
import com.ssafy.presentation.model.Study

class StudyRecyclerAdapter : BaseAdapter<Study>() {
    class StudyHolder(private val binding: ItemMusicListButtonHorizontalBinding) :
        BaseHolder<Study>(binding) {
        override fun bindInfo(data: Study) {
            with(binding) {
                Glide.with(root)
                    .load(data.thumbnail)
                    .into(ivAlbumJacket)
                tvAlbumTitle.text = data.title
                tvAlbumSinger.text = data.singer
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Study> {
        return StudyHolder(
            ItemMusicListButtonHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }
}