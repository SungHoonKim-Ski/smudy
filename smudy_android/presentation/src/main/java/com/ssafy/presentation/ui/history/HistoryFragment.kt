package com.ssafy.presentation.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.base.displayText
import com.ssafy.presentation.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.YearMonth

private const val TAG = "HistoryFragment"

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(
    { FragmentHistoryBinding.bind(it) }, R.layout.fragment_history
) {

    private val historyViewModel = HistoryViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(200)
        val endMonth = currentMonth.plusMonths(200)
        binding.cvCalendar.setup(startMonth, endMonth, daysOfWeek.first())
        binding.cvCalendar.scrollToMonth(currentMonth)
        configureBinders(daysOfWeek)

    }

    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        binding.cvCalendar.apply {
            dayBinder = object : MonthDayBinder<CalendarDayViewContainer> {
                override fun create(view: View) = CalendarDayViewContainer(view).apply {
                    setDayViewClickListener(object : CalendarDayViewContainer.DayViewClickListener {
                        override fun onClick(day: CalendarDay) {
                            with(historyViewModel) {
                                if (selectedDate != day.date) {
                                    val oldDate = selectedDate
                                    selectedDate = day.date
                                    val calendar = this@HistoryFragment.binding.cvCalendar
                                    calendar.notifyDateChanged(day.date)
                                    oldDate?.let { calendar.notifyDateChanged(it) }
//                                    updateAdapterForDate(day.date)
                                }
                            }
                        }

                    })
                }

                override fun bind(container: CalendarDayViewContainer, data: CalendarDay) {
                    container.day = data
                    val binding = container.binding
                    val context = binding.root.context
                    val dayText = binding.tvDay
                    val layout = binding.dayLayout

                    dayText.text = data.date.dayOfMonth.toString()

                    binding.vDayTop.background = null
                    binding.vDayBottom.background = null

                    if (data.position == DayPosition.MonthDate) {
                        dayText.setTextColor(
                            if (historyViewModel.selectedDate == data.date)
                                context.getColor(R.color.white) else context.getColor(R.color.black)
                        )
                        layout.setBackgroundResource(
                            if (historyViewModel.selectedDate == data.date)
                                R.color.calender_selected_gray else R.color.white
                        )
                    } else {
                        dayText.setTextColor(
                            context.getColor(R.color.calendar_unselected_month_text)
                        )
                        layout.setBackgroundResource(R.color.calendar_unselected_month_gray)
                    }
                }
            }

            monthHeaderBinder = object : MonthHeaderFooterBinder<CalendarMonthViewContainer> {
                override fun create(view: View) = CalendarMonthViewContainer(view)

                override fun bind(container: CalendarMonthViewContainer, data: CalendarMonth) {
                    with(container) {
                        monthYearText.text = data.yearMonth.displayText()
                        back.setOnClickListener {

                        }
                        if (layout.tag == null) {
                            layout.tag = data.yearMonth
                            layout.children.map { it as TextView }
                                .forEachIndexed { index, tv ->
                                    with(tv) {
                                        text = daysOfWeek[index].displayText(uppercase = true)
                                    }
                                }
                        }
                    }


                }

            }

        }
    }


}