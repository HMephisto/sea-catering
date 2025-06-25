package com.example.seacatering.ui.condition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.seacatering.databinding.ModalBottomSheetContentBinding
import com.example.seacatering.ui.SubscriptionSelectMealAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetContentBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)

                // Set height to match parent
                sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                sheet.requestLayout()

                // Expand the bottom sheet fully
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    fun setAdapter(adapter: SubscriptionSelectMealAdapter) {
        binding.menuRecycler.adapter = adapter
    }

}