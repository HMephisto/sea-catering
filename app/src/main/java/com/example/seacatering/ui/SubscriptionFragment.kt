package com.example.seacatering.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.seacatering.R
import com.example.seacatering.databinding.FragmentSubcscriptionBinding
import com.example.seacatering.ui.condition.BottomSheetFragment

class SubscriptionFragment : Fragment() {

    private var _binding: FragmentSubcscriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubcscriptionBinding.inflate(inflater, container, false)
        val view =  binding.root

        return view
    }

    private fun setupChooseMeal(){
        val modalBottomSheet = BottomSheetFragment()
        modalBottomSheet.show(childFragmentManager, BottomSheetFragment.TAG)
    }
}