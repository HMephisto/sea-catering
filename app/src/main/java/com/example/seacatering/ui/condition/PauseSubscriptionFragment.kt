package com.example.seacatering.ui.condition

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.seacatering.databinding.FragmentConditionPauseSubscriptionBinding
import com.example.seacatering.databinding.FragmentConditionTwobuttonBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PauseSubscriptionFragment : DialogFragment() {
    private var _binding: FragmentConditionPauseSubscriptionBinding? = null
    private val binding get() = _binding!!

    private var startDate: String = ""
    private var endDate: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConditionPauseSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setOutput()
        setNavigation()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setOutput() {
        dialog?.setCancelable(false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.inputStartDate.setOnClickListener{
            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Start Date")
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            datePicker.show(parentFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val selectedDate = Date(selection)
                val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                binding.inputStartDate.setText(formatter.format(selectedDate))
                startDate = formatter.format(selectedDate)
            }
        }

        binding.inputEndDate.setOnClickListener{
            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select End Date")
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            datePicker.show(parentFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val selectedDate = Date(selection)
                val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                binding.inputEndDate.setText(formatter.format(selectedDate))
                endDate = formatter.format(selectedDate)
            }
        }

    }

    private fun setNavigation(){
        binding.registerConditionButton.setOnClickListener{
            if (startDate.isBlank() or endDate.isBlank()){
                Toast.makeText(requireContext(), "Please select a date", Toast.LENGTH_SHORT).show()
            }else{
                setFragmentResult("pauseResult", bundleOf("startDate" to startDate, "endDate" to endDate))
                dialog!!.dismiss()
            }
        }

        binding.registerSecondConditionButton.setOnClickListener {
            dialog!!.dismiss()
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}