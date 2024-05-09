package com.ssafy.presentation.ui.study.adapter

import android.os.Bundle
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

class StudyRecyclerAdapter(private val studyTypeClickListener: StudyTypeClickListener) :
    PagingDataAdapter<Study, StudyRecyclerAdapter.PagingViewHolder>(diffCallback) {
    inner class PagingViewHolder(private val binding: ItemMusicListButtonHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(musicInfo: Study, position: Int) = with(binding) {
            Log.e("TAG", "bind: $musicInfo")
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
                studyTypeClickListener.fillStudy(idBundle)
            }
            btnPickStudy.setOnClickListener {
                studyTypeClickListener.pickStudy(idBundle)
            }
            btnExpressStudy.setOnClickListener {
                studyTypeClickListener.expressStudy(idBundle)
            }
            btnPronounceStudy.setOnClickListener {
                studyTypeClickListener.pronounceStudy(idBundle)
            }
        }
    }

    interface StudyTypeClickListener {
        fun fillStudy(id: Bundle)
        fun pickStudy(id: Bundle)
        fun expressStudy(id: Bundle)
        fun pronounceStudy(id: Bundle)
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