package com.ssafy.presentation.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ssafy.domain.model.user.StudyReport
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemHistoryBinding
import okhttp3.internal.wait


val historyColorMap = mapOf(
    "FILL" to R.color.dark_purple,
    "PICK" to R.color.light_gray,
    "EXPRESS" to R.color.navy_blue,
    "PRONOUNCE" to R.color.light_pink
)

class HistoryAdapter(private val historyClickListener: HistoryClickListener) :
    BaseAdapter<StudyReport>() {

    inner class HistoryHolder(private val binding: ItemHistoryBinding) :
        BaseHolder<StudyReport>(binding) {
        override fun bindInfo(data: StudyReport) {
            with(binding) {
                with(loBasic) {
                    tvAlbumTitle.text = data.title
                    tvAlbumSinger.text = data.artist
                    Glide.with(root.context)
                        .load(data.jacket)
                        .into(ivAlbumJacket)
                }
                with(btnType) {
                    text = data.type
                    backgroundTintList = ContextCompat.getColorStateList(
                        root.context, historyColorMap[data.type]!!
                    )
                    setOnClickListener { historyClickListener.onClick(data.type,data.id,data.title,data.jacket,data.artist) }
                }
            }
        }
    }

    interface HistoryClickListener {
        fun onClick(type: String, id:Long,title:String,jacket:String,artist:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<StudyReport> {
        return HistoryHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}