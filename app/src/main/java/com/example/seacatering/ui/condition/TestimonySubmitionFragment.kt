package com.example.seacatering.ui.condition

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.seacatering.databinding.FragmentAddTestimonyBinding
import com.example.seacatering.databinding.FragmentConditionTwobuttonBinding

class TestimonySubmitionFragment : DialogFragment() {
    private var _binding: FragmentAddTestimonyBinding? = null
    private val binding get() = _binding!!
    private var selectedItem: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTestimonyBinding.inflate(inflater, container, false)
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

        val items = listOf("  1 Star", "  2 Star" , "  3 Star", "  4 Star", "  5 Star")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        binding.ratingSpinner.setAdapter(adapter)

        binding.ratingSpinner.setOnItemClickListener { _, _, position, _ ->
            selectedItem = position + 1
        }
    }

    private fun setNavigation(){
        binding.registerSecondConditionButton.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.registerConditionButton.setOnClickListener{
            setFragmentResult("testimonyResult", bundleOf("message" to binding.inputTestimony.text.toString(),
                "rating" to selectedItem))
            dialog!!.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}