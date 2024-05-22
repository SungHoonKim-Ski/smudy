package com.ssafy.presentation.ui.study.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemBlankLyricBinding
import com.ssafy.presentation.model.ParcelableSubmitBlankResult


private const val TAG = "BlankResultAdapter"

fun main(){
    val a = "1234"
    a.replaceRange(1,3, "aa")
    println(a)

}
class BlankResultAdapter : BaseAdapter<ParcelableSubmitBlankResult>() {

    class BlankResultHolder(
        private val binding: ItemBlankLyricBinding
    ) : BaseHolder<ParcelableSubmitBlankResult>(binding) {

        override fun bindInfo(data: ParcelableSubmitBlankResult) {


            with(binding) {
                root.setOnClickListener {
                    if(data.originWord.isNotBlank() && !data.isCorrect)
                        clickListener.onClick(layoutPosition)
                }

                tvLyricHeader.setOnClickListener {
                    if(data.originWord.isNotBlank() && !data.isCorrect)
                        clickListener.onClick(layoutPosition)
                }

                val resultColorRes =
                    if (data.isCorrect) R.color.result_blue else R.color.red


                var content = data.lyricBlank

                if(content.isEmpty()){
                    content = "♪"
                    tvLyricHeader.text = content
                }else{
                    if(data.originWord.isNotEmpty()){
                        val blankStart = content.indexOf("_")
                        val blankEnd = content.lastIndexOf("_")
                        content = content.replaceRange(blankStart, blankEnd+1, data.originWord)
                        val spannable = SpannableString(content)
                        spannable.apply{
                            setSpan(
                                UnderlineSpan(),
                                blankStart,
                                blankEnd + 1,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            setSpan(
                                ForegroundColorSpan(root.context.getColor(resultColorRes)),
                                blankStart,
                                blankEnd + 1,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                        tvLyricHeader.text = spannable
                    }else{
                        tvLyricHeader.text = content
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ParcelableSubmitBlankResult> {
        return BlankResultHolder(
            ItemBlankLyricBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        ).apply {
            setOnItemClickListener(clickListener)
        }
    }

}