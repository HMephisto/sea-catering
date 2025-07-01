package com.example.seacatering.ui.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.seacatering.databinding.FragmentAdminStatisticsBinding
import com.example.seacatering.domain.model.Status
import dagger.hilt.android.AndroidEntryPoint
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.mutableMapOf
import kotlin.getValue

@AndroidEntryPoint
class AdminStatisticsFragment : Fragment() {

    private var _binding: FragmentAdminStatisticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminStatisticViewModel by viewModels()

    private var start1: Long = 0
    private var end1: Long = 0

    private var start2: Long = 0
    private var end2: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminStatisticsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputDateRange.setOnClickListener {
            val constraintsBuilder =
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointBackward.now())

            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select date range")
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            picker.show(parentFragmentManager, "date_range_picker")

            picker.addOnPositiveButtonClickListener { dateRange ->
                start1 = dateRange.first
                end1 = dateRange.second

                val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val startDate = sdf.format(Date(start1))
                val endDate = sdf.format(Date(end1))

                viewModel.getNewSubscriptionsData(
                    Timestamp(Date(getStartOfDayUtcMillis(start1))),
                    Timestamp(Date(getEndOfDayUtcMillis(end1)))
                )
                Log.e(
                    "time",
                    "from" + Timestamp(Date(start1)) + "to" + Timestamp(Date(end1 + 24 * 60 * 60 * 1000 - 1))
                )
                binding.inputDateRange.setText(startDate + " - " + endDate)
            }
        }

        binding.inputDateRange2.setOnClickListener {
            val constraintsBuilder =
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointBackward.now())

            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select date range")
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            picker.show(parentFragmentManager, "date_range_picker")

            picker.addOnPositiveButtonClickListener { dateRange ->
                start2 = dateRange.first
                end2 = dateRange.second

                val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val startDate = sdf.format(Date(start2))
                val endDate = sdf.format(Date(end2))

                viewModel.getReactivation(
                    Timestamp(Date(getStartOfDayUtcMillis(start2))),
                    Timestamp(Date(getEndOfDayUtcMillis(end2)))
                )

                binding.inputDateRange2.setText(startDate + " - " + endDate)
            }


        }

        viewModel.getActiveSubscriptionsData()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getNewSubscriptionsDataState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    val createdAtList = result.data as List<Timestamp>
                    newSubscriptionChartData(start1, end1, createdAtList)
                }

                else -> {}
            }
        }

        viewModel.getReactivationState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    val dataList = result.data as List<Timestamp>
                    reactivationChartData(start2, end2, dataList)
                }

                else -> {}
            }
        }

        viewModel.getActiveSubscriptionState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    val dataList = result.data as Map<String, Float>
                    Log.e("DataACtive", dataList.toString())
                    mrrChartData(dataList)
                }

                else -> {}
            }
        }

    }

    private fun newSubscriptionChartData(
        startDateMillis: Long,
        endDateMillis: Long,
        createdAtList: List<Timestamp>,
    ) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val calendar = Calendar.getInstance()
        val dateMap = mutableMapOf<String, Int>()

        calendar.timeInMillis = startDateMillis
        while (calendar.timeInMillis <= endDateMillis) {
            val dateStr = sdf.format(calendar.time)
            dateMap[dateStr] = 0
            calendar.add(Calendar.DATE, 1)
        }

        for (timestamp in createdAtList) {
            val dateStr = sdf.format(timestamp.toDate())
            if (dateMap.containsKey(dateStr)) {
                dateMap[dateStr] = dateMap[dateStr]!! + 1
            }
        }

        val entries = dateMap.entries.mapIndexed { index, entry ->
            Entry(index.toFloat(), entry.value.toFloat())
        }

        val labels = dateMap.keys.toList()

        val dataSet = LineDataSet(entries, "New Subscription").apply {
            setDrawValues(false)
            setDrawCircles(true)
            lineWidth = 2f
        }

        binding.lineChart.axisLeft.apply {
            granularity = 1f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        }

        binding.lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val index = value.toInt()
                    return if (index >= 0 && index < labels.size) labels[index].substring(5) else ""
                }
            }
        }

        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.description.isEnabled = false
        binding.lineChart.data = LineData(dataSet)
        binding.lineChart.invalidate()
    }

    private fun reactivationChartData(
        startDateMillis: Long,
        endDateMillis: Long,
        dataList: List<Timestamp>,
    ) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val calendar = Calendar.getInstance()
        val dateMap = mutableMapOf<String, Int>()

        calendar.timeInMillis = startDateMillis
        while (calendar.timeInMillis <= endDateMillis) {
            val dateStr = sdf.format(calendar.time)
            dateMap[dateStr] = 0
            calendar.add(Calendar.DATE, 1)
        }

        for (timestamp in dataList) {
            val dateStr = sdf.format(timestamp.toDate())
            if (dateMap.containsKey(dateStr)) {
                dateMap[dateStr] = dateMap[dateStr]!! + 1
            }
        }

        val entries = dateMap.entries.mapIndexed { index, entry ->
            Entry(index.toFloat(), entry.value.toFloat())
        }

        val labels = dateMap.keys.toList()

        val dataSet = LineDataSet(entries, "Reactivation").apply {
            setDrawValues(false)
            setDrawCircles(true)
            lineWidth = 2f
        }

        binding.lineChart2.axisLeft.apply {
            granularity = 1f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        }

        binding.lineChart2.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val index = value.toInt()
                    return if (index >= 0 && index < labels.size) labels[index].substring(5) else ""
                }
            }
        }

        binding.lineChart2.axisRight.isEnabled = false
        binding.lineChart2.description.isEnabled = false
        binding.lineChart2.data = LineData(dataSet)
        binding.lineChart2.invalidate()
    }

    private fun mrrChartData(mrrData: Map<String, Float>) {
        val entries = mutableListOf<BarEntry>()
        val labels = mutableListOf<String>()

        mrrData.entries.sortedBy { it.key }.forEachIndexed { index, entry ->
            entries.add(BarEntry(index.toFloat(), entry.value))
            labels.add(entry.key)
        }

        val dataSet = BarDataSet(entries, "MRR (Monthly Revenue)").apply {
            color = Color.parseColor("#3F51B5")
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        val barData = BarData(dataSet)
        binding.mrrChart.data = barData

        binding.mrrChart.axisLeft.setDrawLabels(false)
        binding.mrrChart.axisRight.isEnabled = false
        binding.mrrChart.axisLeft.setDrawGridLines(false)
        binding.mrrChart.axisLeft.setDrawAxisLine(false)

        binding.mrrChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.mrrChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.mrrChart.xAxis.granularity = 1f
        binding.mrrChart.xAxis.labelRotationAngle = -45f
        binding.mrrChart.xAxis.setDrawGridLines(false)
        binding.mrrChart.xAxis.setDrawAxisLine(false)

        binding.mrrChart.description.isEnabled = false
        binding.mrrChart.setTouchEnabled(true)
        binding.mrrChart.setPinchZoom(true)
        binding.mrrChart.setFitBars(true)
        binding.mrrChart.animateY(1000)
        binding.mrrChart.invalidate()
    }


    fun getStartOfDayUtcMillis(millisLocal: Long): Long {
        val localCal = Calendar.getInstance()
        localCal.timeInMillis = millisLocal
        localCal.set(Calendar.HOUR_OF_DAY, 0)
        localCal.set(Calendar.MINUTE, 0)
        localCal.set(Calendar.SECOND, 0)
        localCal.set(Calendar.MILLISECOND, 0)
        return localCal.timeInMillis
    }

    fun getEndOfDayUtcMillis(millisLocal: Long): Long {
        val localCal = Calendar.getInstance()
        localCal.timeInMillis = millisLocal
        localCal.set(Calendar.HOUR_OF_DAY, 23)
        localCal.set(Calendar.MINUTE, 59)
        localCal.set(Calendar.SECOND, 59)
        localCal.set(Calendar.MILLISECOND, 999)
        return localCal.timeInMillis
    }

}