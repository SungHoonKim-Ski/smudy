package com.ssafy.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemMusicListSmallHorizontalBinding
import com.ssafy.presentation.databinding.ItemMusicListSmallVerticalBinding
import com.ssafy.presentation.model.Music

class MainMusicAdapter(private val isVertical: Boolean = true): BaseAdapter<Music>() {

    class VerticalMusicHolder(private val binding: ItemMusicListSmallVerticalBinding) : BaseHolder<Music>(binding){
        override fun bindInfo(data: Music) {
            with(binding){
                Glide.with(root)
                    .load(data.jacket)
                    .into(ivAlbumJacket)

                tvAlbumTitle.text = data.title
            }
        }
    }
    class HorizontalMusicHolder(private val binding: ItemMusicListSmallHorizontalBinding) : BaseHolder<Music>(binding) {
        override fun bindInfo(data: Music) {
            with(binding){
                Glide.with(root)
                    .load(data.jacket)
                    .into(ivAlbumJacket)

                tvAlbumTitle.text = data.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Music> {
        return if(isVertical){
            VerticalMusicHolder(ItemMusicListSmallVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }else{
            HorizontalMusicHolder(ItemMusicListSmallHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}