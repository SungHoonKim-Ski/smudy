package com.ssafy.presentation.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.MonthScrollListener
import com.ssafy.domain.model.ApiResult
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.base.BaseHolder
import com.ssafy.presentation.base.displayText
import com.ssafy.presentation.databinding.FragmentHistoryBinding
import com.ssafy.presentation.model.Music
import com.ssafy.presentation.ui.history.adapter.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val TAG = "HistoryFragment"

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(
    { FragmentHistoryBinding.bind(it) }, R.layout.fragment_history
), HistoryAdapter.HistoryClickListener {

    private val historyViewModel: HistoryViewModel by viewModels()
    private val historyAdapter = HistoryAdapter(this)
    private lateinit var daysOfWeek: List<DayOfWeek>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daysOfWeek = daysOfWeek()

        registerObserve()
        initView()
    }

    private fun initView() {

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(200)
        val endMonth = currentMonth.plusMonths(200)

        with(binding) {
            cvCalendar.setup(startMonth, endMonth, daysOfWeek.first())
            cvCalendar.scrollToMonth(currentMonth)
            cvCalendar.monthScrollListener = object : MonthScrollListener {
                override fun invoke(p1: CalendarMonth) {
                    historyViewModel.setCurrentMonth(p1.yearMonth)
                }
            }
            rvHistory.apply {
                adapter = historyAdapter
            }
        }
        configureBinders(daysOfWeek)

        with(historyViewModel) {
            setCurrentMonth(currentMonth)
        }
    }

    private fun registerObserve() {
        with(historyViewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    history.collect {
                        when (it) {
                            is ApiResult.Failure -> {}
                            is ApiResult.Loading -> {}
                            is ApiResult.Success -> {
                                groupingHistory(it.data)
                            }
                        }
                    }
                }
            }

            lifecycleScope.launch {
                currentMonth.observe(viewLifecycleOwner) {
                    if (it != null) {
                        Log.d(TAG, "registerObserve: $it")
                        getHistory(
                            it.atStartOfMonth().atStartOfDay(ZoneId.of("Asia/Seoul"))
                                .toInstant()
                                .toEpochMilli()
                        )
                    }
                }
            }

            lifecycleScope.launch {
                historyGroup.observe(viewLifecycleOwner) {
                    configureBinders(daysOfWeek)
                }
            }

            lifecycleScope.launch {
                selectedDate.observe(viewLifecycleOwner) {
                    if (it != null) {
                        binding.tvDate.text = it.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                        historyAdapter.submitList(
                            historyGroup.value!![it]
                        )
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                navigationTrigger.collectLatest {
                    when (it) {
                        "EXPRESS" -> {
                            findNavController().navigate(
                                HistoryFragmentDirections.actionHistoryFragmentToExpressResultFragment(
                                    historyViewModel.getSelectedMusicInfo(),
                                    historyViewModel.getExpressHistoryResult().toTypedArray(),
                                    true
                                )
                            )
                        }

                        "FILL" -> {
                            findNavController().navigate(
                                HistoryFragmentDirections.actionHistoryFragmentToFillResultFragment(
                                    historyViewModel.getFillHistoryResult(), true
                                )
                            )
                        }

                        "PICK" -> {
                            findNavController().navigate(
                                HistoryFragmentDirections.actionHistoryFragmentToShuffleResultFragment(
                                    historyViewModel.getPickHistoryResult(), true
                                )
                            )
                        }

                        "PRONOUNCE" -> {
                            findNavController().navigate(
                                HistoryFragmentDirections.actionHistoryFragmentToPronounceResultFragment(
                                    historyViewModel.getPronounceHistoryResult(),
                                    historyViewModel.getSelectedMusicInfo(),
                                    true
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        binding.cvCalendar.apply {
            dayBinder = object : MonthDayBinder<CalendarDayViewContainer> {
                override fun create(view: View) = CalendarDayViewContainer(view.apply {
                    updateLayoutParams {
                        height = dpToPx(
                            resources.getInteger(R.integer.history_calendar_day_size),
                            requireContext()
                        )
                    }
                }).apply {
                    setDayViewClickListener(object : CalendarDayViewContainer.DayViewClickListener {
                        @SuppressLint("SimpleDateFormat")
                        override fun onClick(day: CalendarDay) {
                            with(historyViewModel) {
                                if (selectedDate.value != day.date) {
                                    val oldDate = selectedDate.value
                                    setSelectedDate(day.date)
                                    val calendar = this@HistoryFragment.binding.cvCalendar
                                    calendar.notifyDateChanged(day.date)
                                    oldDate?.let { calendar.notifyDateChanged(it) }
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

                    val jacket = historyViewModel.historyGroup.value!![data.date]
                    if (jacket != null) {
                        Glide.with(requireContext())
                            .load(jacket[0].jacket)
                            .into(binding.ivAlbumJacket)
                    }
                    if (data.position == DayPosition.MonthDate) {
                        dayText.setTextColor(
                            if (historyViewModel.selectedDate.value == data.date)
                                context.getColor(R.color.white) else context.getColor(R.color.black)
                        )
                        layout.setBackgroundResource(
                            if (historyViewModel.selectedDate.value == data.date)
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
                            findFirstVisibleMonth()?.let {
                                binding.cvCalendar.smoothScrollToMonth(it.yearMonth.previousMonth)
                            }
                        }
                        forward.setOnClickListener {
                            findFirstVisibleMonth()?.let {
                                binding.cvCalendar.smoothScrollToMonth(it.yearMonth.nextMonth)
                            }

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

    override fun onClick(type: String, id: Long, title: String, jacket: String, artist: String) {
        when (type) {
            "EXPRESS" -> {
                historyViewModel.getExpressHistory(id, title, jacket, artist)
            }

            "FILL" -> {
                historyViewModel.getFillHistory(id, title, jacket, artist)
            }

            "PICK" -> {
                historyViewModel.getPickHistory(id, title, jacket, artist)
            }

            "PRONOUNCE" -> {
                historyViewModel.getPronounceHistory(id, title, jacket, artist)
            }
        }
    }
}