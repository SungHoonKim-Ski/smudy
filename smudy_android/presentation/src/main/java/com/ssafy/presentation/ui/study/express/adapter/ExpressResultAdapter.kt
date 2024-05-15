package com.ssafy.presentation.ui.study.express.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.presentation.base.BaseAdapter
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.databinding.ItemExpressResultBinding
import com.ssafy.presentation.model.express.ExpressResult

class ExpressResultAdapter : BaseAdapter<ExpressResult>() {
    class ExpressHolder(private val binding: ItemExpressResultBinding) :
        BaseHolder<ExpressResult>(binding) {
        override fun bindInfo(data: ExpressResult) {
            with(binding) {
                tvRecommendedEnglish.text = data.suggestedSentenceEn
                tvRecommendedKorean.text = data.suggestedSentenceKo
                tvAnswerEnglish.text = data.userAnswerSentenceEn
                tvAnswerKorean.text = data.userAnswerSentenceKo
                tvReportTitle.text = "#${layoutPosition + 1}: "
                tvReportScore.text = data.score.toString()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ExpressResult> {
        return ExpressHolder(
            ItemExpressResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}