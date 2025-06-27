package com.example.seacatering.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.seacatering.databinding.FragmentHomeBinding
import com.example.seacatering.domain.model.Menu
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Testimony
import com.example.seacatering.ui.menu.MenuAdapter
import com.example.seacatering.ui.menu.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val adapter: HomeTestimonialsAdapter = HomeTestimonialsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view =  binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListView()
        setupRequest()
        setupObserver()
    }

    private fun initListView() {
        binding.menuRecycler.adapter = adapter
    }

    private fun setupRequest() {
        viewModel.getTestimonials()
    }

    private fun setupObserver() {
        viewModel.getTestimonialsState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    val testimonials = result.data as List<Testimony>
                    adapter.items = testimonials
                }

                is Status.Failure -> {
                    Log.e("Failure", result.message)
                }

                is Status.Success -> {
                    Log.e("success", "load")
                }

                is Status.Loading -> {
                    Log.e("LOADING", "load")
                }

                else -> {}
            }
        }
    }
}