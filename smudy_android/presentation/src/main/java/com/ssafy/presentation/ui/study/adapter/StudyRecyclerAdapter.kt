package com.ssafy.presentation.ui.study.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.presentation.databinding.ItemMusicListButtonHorizontalBinding
import com.ssafy.presentation.model.Study

class StudyRecyclerAdapter(private val studyTypeClickListener: StudyTypeClickListener) :
    PagingDataAdapter<Study, StudyRecyclerAdapter.PagingViewHolder>(diffCallback) {
    inner class PagingViewHolder(private val binding: ItemMusicListButtonHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(musicInfo: Study) = with(binding) {
            tvAlbumTitle.text = musicInfo.title
            tvAlbumSinger.text = musicInfo.singer
            Glide.with(root).load(musicInfo.thumbnail).into(ivAlbumJacket)
            llStudyType.isVisible = musicInfo.flag
            btnExpand.setOnClickListener {
                llStudyType.isVisible = !llStudyType.isVisible
                musicInfo.flag = !musicInfo.flag
            }
            val idBundle = Bundle()
            idBundle.putString("id", musicInfo.id)
            btnFillStudy.setOnClickListener {
                studyTypeClickListener.fillStudy(musicInfo.id)
            }
            btnPickStudy.setOnClickListener {
                studyTypeClickListener.pickStudy(musicInfo.id)
            }
            btnExpressStudy.setOnClickListener {
                studyTypeClickListener.expressStudy(musicInfo.id)
            }
            btnPronounceStudy.setOnClickListener {
                studyTypeClickListener.pronounceStudy(musicInfo.id)
            }
        }
    }

    interface StudyTypeClickListener {
        fun fillStudy(id: String)
        fun pickStudy(id: String)
        fun expressStudy(id: String)
        fun pronounceStudy(id: String)
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
            holder.bind(it)
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