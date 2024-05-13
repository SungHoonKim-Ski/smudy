package com.ssafy.presentation.ui.study.fill


import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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

        //        private fun getSpannable(
//            context: Context,
//            origin: String,
//            start: Int,
//            end: Int,
//            @ColorRes colorRes: Int,
//            tmpTarget: EditText
//        ) = SpannableString(origin).apply {
//            val inputMethodManager =
//                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            setSpan(object : ClickableSpan() {
//                override fun updateDrawState(textPaint: TextPaint) {
//                    textPaint.color = context.getColor(colorRes)
//                    textPaint.isUnderlineText = true
//                }
//
//                override fun onClick(widget: View) {
//                    tmpTarget.requestFocus()
//                    inputMethodManager.showSoftInput(
//                        tmpTarget,
//                        InputMethodManager.SHOW_IMPLICIT
//                    )
//                }
//            }, start, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        }
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
                    if (layoutPosition == adapter.curPosition) R.color.black else R.color.light_gray
                tvLyricHeader.setTextColor(root.context.getColor(colorRes))
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
                        Log.d(TAG, "exc: ${etLyric.text}")
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