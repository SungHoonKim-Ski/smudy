package com.ssafy.presentation.ui.study.fill

import android.content.Context
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import com.ssafy.domain.model.LyricBlank
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemBlankLyricBinding
import com.ssafy.presentation.model.BlankQuestion
import kotlin.math.log


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

        interface InputPasser{
            fun onPass( input: String, position: Int)
            fun onGetInput( position: Int ): String
        }

        private lateinit var passer: InputPasser
        fun setPasser( passer: InputPasser ){
            this.passer = passer
        }


        private fun getSpannable(
            context: Context,
            origin: String,
            start: Int,
            end: Int,
            @ColorRes colorRes: Int,
            tmpTarget: EditText
        ) = SpannableString(origin).apply {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            setSpan(object : ClickableSpan() {

                override fun updateDrawState(textPaint: TextPaint) {
                    textPaint.color = context.getColor(colorRes)
                    textPaint.isUnderlineText = true
                }

                override fun onClick(widget: View) {
                    tmpTarget.requestFocus()
                    inputMethodManager.showSoftInput(
                        tmpTarget,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            }, start, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        override fun bindInfo(data: BlankQuestion) {
            with(binding) {

                val colorRes = if(layoutPosition == adapter.curPosition) R.color.black else R.color.light_gray

                tvLyricHeader.setTextColor(root.context.getColor(colorRes))


                val inputMethodManager =
                    root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                val lyricBlank = data.lyricBlank
                val blankIdx = data.blankStart
                val lastBlankIdx = data.blankEnd

                var sub = lyricBlank.replace("_", " ")


                var input = data.inputAnswer.replace("_", " ")
                var before = ""
                var after = ""
                var content = SpannableString("")

                if (blankIdx != -1) {
                    before = lyricBlank.substring(0, blankIdx)
                    after = lyricBlank.substring(lastBlankIdx + 1, lyricBlank.lastIndex+1)
                }

                val maxLen = data.lyricBlankAnswer.length

                if(blankIdx != -1){
                    content = getSpannable(root.context, before + input + after, blankIdx, lastBlankIdx, colorRes, etInput)
                }

                tvLyricHeader.text = content
                tvLyricHeader.movementMethod = LinkMovementMethod.getInstance()

                etInput.apply {
//                    onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
//                        run {
//                            if (!hasFocus) {
//                                inputMethodManager.hideSoftInputFromWindow(
//                                    etInput.windowToken,
//                                    InputMethodManager.HIDE_NOT_ALWAYS
//                                )
//
//                            }
//                        }
//                    }

                    addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                        override fun afterTextChanged(s: Editable?) {
                            input = s.toString()
                            if(input.all{ it == ' ' }){
                                input = ""
                            }
                            if (input.length >= maxLen) {
                                s!!.delete(maxLen, input.length)
                            }else{
                                input += (" ".repeat(maxLen - input.length))
                            }

                            passer.onPass(input.replace(" ", "_"), layoutPosition)

                            sub = before + input + after
                            content = getSpannable(root.context, sub, blankIdx, lastBlankIdx, colorRes, etInput)
                            tvLyricHeader.text = content
                            tvLyricHeader.movementMethod = LinkMovementMethod.getInstance()
                        }
                    })
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
        ).apply{
            setPasser(passer)
        }
    }

    private lateinit var passer: BlankLyricHolder.InputPasser
    fun setPasser( passer: BlankLyricHolder.InputPasser ){
        this.passer = passer
    }

}