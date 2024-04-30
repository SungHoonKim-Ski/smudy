package com.ssafy.presentation.ui.study.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.presentation.databinding.ItemMusicListButtonHorizontalBinding
import com.ssafy.presentation.model.Study

class StudyRecyclerAdapter :
    PagingDataAdapter<Study, StudyRecyclerAdapter.PagingViewHolder>(diffCallback) {
    inner class PagingViewHolder(private val binding: ItemMusicListButtonHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(musicInfo: Study, position: Int) = with(binding) {
            Log.e("TAG", "bind: $musicInfo")
            this.tvAlbumTitle.text = musicInfo.title
            this.tvAlbumSinger.text = musicInfo.singer
            Glide.with(root).load(musicInfo.thumbnail).into(this.ivAlbumJacket)
            llStudyType.isVisible = musicInfo.expanded
            this.btnExpand.setOnClickListener {
                this.llStudyType.isVisible = !this.llStudyType.isVisible
                musicInfo.expanded = !musicInfo.expanded
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

    override fun onBindViewHolder(holder: StudyRecyclerAdapter.PagingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudyRecyclerAdapter.PagingViewHolder {
        return PagingViewHolder(
            ItemMusicListButtonHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}