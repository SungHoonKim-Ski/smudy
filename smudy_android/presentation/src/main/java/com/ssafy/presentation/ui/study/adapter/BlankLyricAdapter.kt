package com.ssafy.presentation.ui.study.adapter


import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemBlankLyricBinding
import com.ssafy.presentation.model.BlankQuestion
import com.ssafy.presentation.model.toQuestion

private const val TAG = "BlankLyricAdapter"

class BlankLyricAdapter : BaseAdapter<BlankQuestion>() {

    private var _curPosition = -1
    val curPosition: Int
        get() = _curPosition

    fun setCurPosition(position: Int) {
        _curPosition = position
    }

    class BlankLyricHolder(
        private val binding: ItemBlankLyricBinding,
        private val adapter: BlankLyricAdapter
    ) : BaseHolder<BlankQuestion>(binding) {

        override fun bindInfo(data: BlankQuestion) {

//            if(data.lyricBlankAnswer.isBlank()){
//                binding.root.isClickable = false
//                binding.tvLyricHeader.isClickable = false
//            }

            with(binding) {
                root.setOnClickListener {
                    if(data.lyricBlankAnswer.isNotBlank())
                        clickListener.onClick(layoutPosition)
                }

                tvLyricHeader.setOnClickListener {
                    if(data.lyricBlankAnswer.isNotBlank())
                        clickListener.onClick(layoutPosition)
                }


                val colorRes =
                    if (layoutPosition == adapter.curPosition) R.color.white else R.color.black

                val backgroundColorRes =
                    if (layoutPosition == adapter.curPosition) R.color.fill_selected else R.color.white

                tvLyricHeader.setTextColor(root.context.getColor(colorRes))


                root.setBackgroundColor(root.context.getColor(backgroundColorRes))
                val content = data.toQuestion()

                if (data.blankStart != -1) {
                    try {
                        val spannable = SpannableString(content)
                        spannable.setSpan(
                            UnderlineSpan(),
                            data.blankStart,
                            data.blankEnd + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        tvLyricHeader.text = spannable
                    } catch (e: Exception) {
                        Log.d(TAG, "bindInfo: $e")
                    }
                }else if(data.lyricBlank.isNotEmpty()) {
                    tvLyricHeader.text = data.lyricBlank
                }else{
                    tvLyricHeader.text = "♪"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<BlankQuestion> {
        return BlankLyricHolder(
            ItemBlankLyricBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            this
        ).apply {
            setOnItemClickListener(clickListener)
        }
    }

}