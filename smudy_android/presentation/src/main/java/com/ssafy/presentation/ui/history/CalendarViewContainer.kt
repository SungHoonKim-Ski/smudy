package com.ssafy.presentation.ui.history

import android.view.View
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.ViewContainer
import com.ssafy.presentation.databinding.HistoryCalendarDayBinding
import com.ssafy.presentation.databinding.HistoryCalendarHeaderBinding

class CalendarDayViewContainer(view: View) : ViewContainer(view){
    lateinit var day: CalendarDay // Will be set when this container is bound.
    val binding = HistoryCalendarDayBinding.bind(view)
    interface DayViewClickListener{
        fun onClick(day: CalendarDay)
    }
    private lateinit var listener: DayViewClickListener
    fun setDayViewClickListener(listener: DayViewClickListener){
        this.listener = listener
    }
    init {
        view.setOnClickListener {
            if (day.position == DayPosition.MonthDate) {
                listener.onClick(day)
            }
        }
    }
}

class CalendarMonthViewContainer(view: View) : ViewContainer(view) {
    private val binding = HistoryCalendarHeaderBinding.bind(view)
    val monthYearText = binding.tvMonthYear
    val layout = binding.llDayContainer.root
    val back = binding.ivMonthBack
    val forward = binding.ivMonthForward
}