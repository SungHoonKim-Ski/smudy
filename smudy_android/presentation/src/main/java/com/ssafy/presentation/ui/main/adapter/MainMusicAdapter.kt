package com.ssafy.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ssafy.domain.model.user.Song
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemMusicListSmallHorizontalBinding
import com.ssafy.presentation.databinding.ItemMusicListSmallVerticalBinding

class MainMusicAdapter(private val isVertical: Boolean = true): BaseAdapter<Song>() {

    class VerticalMusicHolder(private val binding: ItemMusicListSmallVerticalBinding) : BaseHolder<Song>(binding){
        override fun bindInfo(data: Song) {
            with(binding){
                Glide.with(root)
                    .load(data.jacket)
                    .into(ivAlbumJacket)

                tvAlbumTitle.text = data.title
                tvAlbumSinger.text = data.artist
            }
        }
    }
    class HorizontalMusicHolder(private val binding: ItemMusicListSmallHorizontalBinding) : BaseHolder<Song>(binding) {
        override fun bindInfo(data: Song) {
            with(binding){
                Glide.with(root)
                    .load(data.jacket)
                    .into(ivAlbumJacket)

                tvAlbumTitle.text = data.title
                tvAlbumSinger.text = data.artist
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Song> {
        return if(isVertical){
            VerticalMusicHolder(ItemMusicListSmallVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }else{
            HorizontalMusicHolder(ItemMusicListSmallHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}