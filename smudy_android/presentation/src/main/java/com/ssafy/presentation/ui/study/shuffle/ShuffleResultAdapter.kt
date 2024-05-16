package com.ssafy.presentation.ui.study.shuffle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.domain.model.ShuffleQuestionProblem
import com.ssafy.domain.model.ShuffleSubmitResult
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemShuffleResultBinding

class ShuffleResultAdapter(private val isCorrect: Boolean = true) : BaseAdapter<ShuffleQuestionProblem>() {

    class ShuffleResultHolder(
        private val binding: ItemShuffleResultBinding,
        private val isCorrect: Boolean
    ) : BaseHolder<ShuffleQuestionProblem>(binding) {
        override fun bindInfo(data: ShuffleQuestionProblem) {
            val colorRes =
                if (isCorrect)
                    R.color.correct_answer_box_yellow
                else
                    R.color.incorrect_answer_box_red

            with(binding) {
                root.setCardBackgroundColor(
                    root.context.getColor(colorRes)
                )
                if(isCorrect){
                    tvLyricInput.visibility = View.GONE
                }
                tvLyricEn.text = data.lyricSentenceEn
                tvLyricInput.text = data.userLyricSentence
                tvLyricKr.text = data.lyricSentenceKo
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<ShuffleQuestionProblem> {
        return ShuffleResultHolder(
            ItemShuffleResultBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            isCorrect
        )
    }
}