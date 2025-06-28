package com.example.seacatering.ui.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.seacatering.databinding.FragmentAdminSubscriptionBinding
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.SubscriptionWithMenuAndUser
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class AdminSubscriptionFragment : Fragment(), AdminSubscriptionAdapter.OnItemClickListener {

    private var _binding: FragmentAdminSubscriptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminSubscriptionViewModel by viewModels()
    private val adapter: AdminSubscriptionAdapter = AdminSubscriptionAdapter(this)

    private var selectedOption : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminSubscriptionBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("Active", "Paused" , "Canceled")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        binding.ratingSpinner.setAdapter(adapter)

        binding.ratingSpinner.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    selectedOption = "Active"
                }
                1 -> {
                    selectedOption = "Paused"
                }
                2 -> {
                    selectedOption = "Canceled"
                }
            }
            binding.ratingSpinner.hint = selectedOption
            setupRequest(selectedOption)
        }

        initListView()
        setupRequest("")
        swipeRefresh()
        setupObserver()
    }

    private fun initListView() {
        binding.menuRecycler.adapter = adapter
    }

    private fun setupRequest(status: String) {
        viewModel.getAllSubscriptions(status)
    }

    private fun swipeRefresh() {
        binding.swiperRefreshMenu.setOnRefreshListener {
            setupRequest(selectedOption)
            binding.swiperRefreshMenu.isRefreshing = true
        }
    }

    private fun setupObserver() {
        viewModel.getAllSubscriptionState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    val subsciptions = result.data as List<SubscriptionWithMenuAndUser>
                    adapter.items = subsciptions
                    binding.swiperRefreshMenu.isRefreshing = false
                }

                is Status.Failure -> {
                    Log.e("Failure", result.message)
                }

                is Status.Success -> {
                    Log.e("success", "load")
                }

                is Status.Loading -> {
                    Log.e("LOADIN", "load")
                }

                else -> {}
            }
        }
    }

    override fun onItemClick(item: SubscriptionWithMenuAndUser) {
    }
}