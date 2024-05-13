package com.ssafy.presentation.ui.study.pronounce.dialog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.ssafy.presentation.R
import com.ssafy.presentation.databinding.DialogPronouncePitchBinding
import com.ssafy.presentation.model.pronounce.IntensityData
import com.ssafy.presentation.model.pronounce.Timestamp

class PronounceIntensityDialog(
    private val ttsPitchData: IntensityData,
    private val userPitchData: IntensityData,
    private val wordData: List<Timestamp>,
    private val context: Context
) : DialogFragment() {
    private var _binding: DialogPronouncePitchBinding? = null
    private val binding get() = _binding!!
    private val ttsLineDataSet: LineDataSet by lazy {
        makeLineDataSet(R.color.tts_graph,makeDataSet(ttsPitchData,wordData),"Smudy")
    }
    private val userLineDataSet: LineDataSet by lazy {
        makeLineDataSet(R.color.user_graph,makeDataSet(userPitchData),"user")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            // 화면 너비에 맞춰 다이얼로그의 너비 설정
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogPronouncePitchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initEvent() {
        with(binding) {
            ivClose.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun initView() {
        with(binding) {
            val xAxis = lcPitchChart.xAxis
            lcPitchChart.axisLeft.isEnabled = false
            lcPitchChart.axisRight.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)  // 격자선을 그리지 않음
            lcPitchChart.description.isEnabled=false
            lcPitchChart.data = LineData(ttsLineDataSet,userLineDataSet)
            lcPitchChart.invalidate()
        }
    }

    private fun makeLineDataSet(lineColor: Int, entry: List<Entry>, label: String)=
        LineDataSet(entry, label).apply {
            color = ContextCompat.getColor(context,lineColor)
            valueTextColor = Color.BLACK
            lineWidth = 2f
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
        }


    private fun makeDataSet(pitchData: IntensityData, timeStamp: List<Timestamp>) =
        ArrayList<Entry>().apply {
            pitchData.values.forEachIndexed { index, data ->
                add(Entry(timeStamp[index].time.toFloat(), data.toFloat()))
            }
        }

    private fun makeDataSet(pitchData: IntensityData) =
        ArrayList<Entry>().apply {
            pitchData.times.forEachIndexed { index, data ->
                add(Entry(data.toFloat(), pitchData.values[index].toFloat()))
            }
        }
}