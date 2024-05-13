package com.ssafy.presentation.ui.study.fill

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ssafy.presentation.R
import com.ssafy.presentation.databinding.ItemBlankLyricBinding

class LyricView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val binding: ItemBlankLyricBinding by lazy {
        ItemBlankLyricBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.item_blank_lyric, this, false)
        )
    }

    init{
        addView(binding.root)
    }


}