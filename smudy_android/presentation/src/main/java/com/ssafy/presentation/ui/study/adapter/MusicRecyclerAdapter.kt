package com.ssafy.presentation.ui.study.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.presentation.databinding.ItemSearchMusicResultBinding
import com.ssafy.presentation.model.Study

class MusicRecyclerAdapter(private val onClick: (String, Boolean) -> (Unit)) :
    PagingDataAdapter<Study, MusicRecyclerAdapter.PagingViewHolder>(diffCallback) {
    inner class PagingViewHolder(private val binding: ItemSearchMusicResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(musicInfo: Study) = with(binding) {
            tvAlbumTitle.text = musicInfo.title
            tvAlbumSinger.text = musicInfo.singer
            Glide.with(root).load(musicInfo.thumbnail).into(ivAlbumJacket)
            btnCheck.apply {
                isChecked = musicInfo.flag
                setOnClickListener {
                    musicInfo.flag = isChecked
                    if (isChecked) {
                        onClick(musicInfo.id, true)
                    } else {
                        onClick(musicInfo.id, false)
                    }
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Study>() {

            override fun areItemsTheSame(oldItem: Study, newItem: Study): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Study, newItem: Study): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MusicRecyclerAdapter.PagingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicRecyclerAdapter.PagingViewHolder {
        return PagingViewHolder(
            ItemSearchMusicResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}