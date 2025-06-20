package com.example.seacatering.ui.condition

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.seacatering.databinding.FragmentConditionBinding
import com.example.seacatering.R


class AuthLoginErrorCondition : DialogFragment() {
    private var _binding: FragmentConditionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOutput()
        setNavigation()
    }

    private fun setOutput(){
        dialog?.setCancelable(false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.registerConditionTitle.text = "Gagal Masuk!"
        binding.registerConditionContent.text = "Periksa Email dan Kata Sandi"
        binding.registerConditionButton.text = "Coba Lagi"
        binding.registerConditionButton.backgroundTintList = context?.getColorStateList(R.color.error)
        binding.registerConditionButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }


    private fun setNavigation(){
        binding.registerConditionButton.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}