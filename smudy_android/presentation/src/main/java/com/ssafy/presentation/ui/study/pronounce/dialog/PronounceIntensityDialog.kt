package com.ssafy.presentation.ui.study.pronounce.dialog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.ssafy.presentation.R
import com.ssafy.presentation.databinding.DialogPronounceIntensityBinding
import com.ssafy.presentation.model.pronounce.IntensityData
import com.ssafy.presentation.model.pronounce.Timestamp

class PronounceIntensityDialog(
    private val ttsIntensityData: List<IntensityData>,
    private val userIntensityData: List<IntensityData>,
    private val wordData: List<Timestamp>,
    private val context: Context
) : DialogFragment() {
    private var _binding: DialogPronounceIntensityBinding? = null
    private val binding get() = _binding!!
    private val ttsLineDataSet: LineDataSet by lazy {
        makeLineDataSet(R.color.tts_graph,makeDataSet(ttsIntensityData),"Smudy")
    }
    private val userLineDataSet: LineDataSet by lazy {
        makeLineDataSet(R.color.user_graph,makeDataSet(userIntensityData),"user")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.RoundedDialog)
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
        _binding = DialogPronounceIntensityBinding.inflate(inflater, container, false)
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

            // X축 레이블을 강제로 설정
            xAxis.granularity = 0.00000001f // 최소 간격을 1로 설정
            xAxis.isGranularityEnabled = true // 간격을 강제 적용

            xAxis.apply {
                wordData.map {
                    addLimitLine(
                        LimitLine(it.startTime.toFloat(),"").apply {
                            lineWidth = 1f
                            enableDashedLine(10f,10f,0f)
                            lineColor = ContextCompat.getColor(context, R.color.calender_selected_gray)
                        }
                    )
                    addLimitLine(
                        LimitLine(((it.endTime + it.startTime)/2).toFloat(),it.word).apply {
                            lineColor = Color.TRANSPARENT
                            lineWidth = 0f
                            labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
                            textSize = 13f
                        }
                    )
                    addLimitLine(
                        LimitLine(it.endTime.toFloat(),"").apply {
                            lineWidth = 1f
                            enableDashedLine(10f,10f,0f)
                            lineColor = ContextCompat.getColor(context, R.color.calender_selected_gray)
                        }
                    )
                }
            }
            xAxis.setLabelCount(wordData.size, true) // 레이블 수를 강제 설정
            val maxUserTime = wordData.last().endTime.toFloat()
            xAxis.axisMaximum = maxUserTime + 0.1f

            xAxis.setDrawGridLines(false) // 격자선을 그리지 않음
            xAxis.setDrawLabels(false)
            xAxis.setDrawAxisLine(false)

            lcPitchChart.isDragEnabled = true
            lcPitchChart.zoom(3f, 1f, 0f, 0f, YAxis.AxisDependency.LEFT)
            lcPitchChart.setPinchZoom(false)
            lcPitchChart.isScaleYEnabled = false
            lcPitchChart.description.isEnabled = false
            lcPitchChart.setScaleEnabled(true)
            lcPitchChart.data = LineData(ttsLineDataSet, userLineDataSet)
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


    private fun makeDataSet(intensityDatas: List<IntensityData>) =
        ArrayList<Entry>().apply {
            intensityDatas.map { intensityData ->
                intensityData.values.forEachIndexed { index, data ->
                    add(Entry(intensityData.times[index].toFloat(), data.toFloat()))
                }
            }
        }
}