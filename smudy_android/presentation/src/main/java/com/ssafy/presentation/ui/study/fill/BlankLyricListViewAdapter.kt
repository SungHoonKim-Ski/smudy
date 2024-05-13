package com.ssafy.presentation.ui.study.fill

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.BaseAdapter
import androidx.core.widget.addTextChangedListener
import com.ssafy.presentation.R
import com.ssafy.presentation.databinding.ItemBlankLyricBinding
import com.ssafy.presentation.model.BlankQuestion

private const val TAG = "BlankLyricListViewAdapt"

class BlankLyricListViewAdapter(

) : BaseAdapter() {

    private var _curPosition = -1
    val curPosition: Int
        get() = _curPosition

    fun setCurPosition(position: Int) {
        _curPosition = position
    }

    private val items: MutableList<BlankQuestion> = mutableListOf()

    fun setItems(items: List<BlankQuestion>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    interface InputPasser {
        fun onPass(input: String, position: Int)
        fun onGetInput(position: Int): String
    }

    private lateinit var passer: InputPasser
    fun setPasser(passer: InputPasser) {
        this.passer = passer
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = (if (convertView == null)
            ItemBlankLyricBinding.inflate(LayoutInflater.from(parent?.context))
                .apply { root.tag = this }
        else convertView.tag) as ItemBlankLyricBinding
        bind(binding, position)
        return binding.root
    }

    private fun bind(binding: ItemBlankLyricBinding, position: Int) {
        val data = items[position]
        val colorRes = if (position == curPosition) R.color.black else R.color.light_gray
        with(binding) {
            etLyric.setTextColor(root.context.getColor(colorRes))
            val lyricBlank = data.lyricBlank
            val blankIdx = data.blankStart
            val lastBlankIdx = data.blankEnd

            val input = data.inputAnswer
            var before = data.lyricBlank
            var after = ""
            val maxLen = data.lyricBlankAnswer.length

            if (blankIdx != -1) {
                before = lyricBlank.substring(0, blankIdx)
                after = lyricBlank.substring(lastBlankIdx + 1, lyricBlank.lastIndex + 1)
            }

            val content = before + input + after

            root.isClickable = false

            etLyric.setOnClickListener {
                Log.d(TAG, "bindet: $position ${etLyric.text}")
                etLyric.requestFocus()

            }
//            root.setOnClickListener {
//                Log.d(TAG, "bindroot: $position  ${etLyric.text}")
//                etLyric.performClick()
//            }

            etLyric.setText(content)

            Log.d(TAG, "bind: $content")

            if (blankIdx > -1) {
                (etLyric.text as Spannable).setSpan(
                    UnderlineSpan(), blankIdx, lastBlankIdx,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

//                etLyric.apply {
//                    addTextChangedListener {
//                        if(this.selectionEnd > blankIdx + maxLen){
//                            setText(text.removeRange(this.selectionEnd-1, this.selectionEnd))
//                            post{
//                                setSelection(lastBlankIdx+1)
//                            }
//                        }
//
//                        if(this.selectionStart < blankIdx ){
//                            this.setSelection(blankIdx)
//                        }
//
//                        setOnFocusChangeListener { v, hasFocus ->
//                            if(!hasFocus){
//                                passer.onPass(this.text.toString().substring(blankIdx, lastBlankIdx) , position)
//                            }else{
//                                if(this.selectionStart>blankIdx+maxLen || this.selectionStart<blankIdx){
//                                    etLyric.post{
//                                        setSelection(blankIdx)
//                                    }
//                                }
//
//                            }
//                        }
//
//                    }
//                }
            } else {
                etLyric.setText(before)
                etLyric.isEnabled = false
            }

        }

    }
}