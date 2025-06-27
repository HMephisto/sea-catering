package com.example.seacatering.ui.condition

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.seacatering.databinding.FragmentConditionTwobuttonBinding
import java.util.Calendar

class CancelConfirmationCondition : DialogFragment() {
    private var _binding: FragmentConditionTwobuttonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConditionTwobuttonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setOutput()
        setNavigation()
    }

    override fun onStart() {
        super.onStart()

    }

    private fun setOutput() {
        dialog?.setCancelable(false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.registerConditionTitle.text = "Are you sure want to cancel the subscription?"
        binding.registerConditionContent.visibility = View.GONE
    }

    private fun setNavigation(){
        binding.registerSecondConditionButton.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.registerConditionButton.setOnClickListener{
            Log.e("Done", "halo")
            setFragmentResult("cancelResult", bundleOf("cancel" to true))
            dialog!!.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}