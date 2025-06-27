package com.example.seacatering.ui.condition

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.seacatering.R
import com.example.seacatering.databinding.FragmentConditionBinding

class LoadingCondition : DialogFragment() {
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
    }

    override fun onStart() {
        super.onStart()
    }

    private fun setOutput() {
        dialog?.setCancelable(false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        binding.registerConditionImage.setImageResource(R.drawable.loading_spinner)

        val rotateAnimation = AnimationUtils.loadAnimation(
            binding.registerConditionImage.context,
            R.anim.rotate_animation
        )

        binding.registerConditionImage.startAnimation(rotateAnimation)

        binding.registerConditionTitle.visibility = View.GONE
        binding.registerConditionContent.visibility = View.GONE
        binding.registerConditionButton.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}