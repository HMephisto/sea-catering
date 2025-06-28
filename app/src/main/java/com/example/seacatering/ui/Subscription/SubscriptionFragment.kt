package com.example.seacatering.ui.Subscription

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.seacatering.R
import com.example.seacatering.databinding.FragmentSubcscriptionBinding
import com.example.seacatering.domain.model.Menu
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.SubscriptionWithMenu
import com.example.seacatering.ui.condition.CancelConfirmationCondition
import com.example.seacatering.ui.condition.PauseSubscriptionFragment
import com.example.seacatering.ui.condition.StartSubscriptionFragment
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class SubscriptionFragment : Fragment(), SubscriptionAdapter.OnItemClickListener {

    private var _binding: FragmentSubcscriptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubscriptionViewModel by viewModels()
    private val adapter: SubscriptionAdapter = SubscriptionAdapter(this)

    private val cancelModal = CancelConfirmationCondition()
    private val pauseModal = PauseSubscriptionFragment()
    private val startModal = StartSubscriptionFragment()

    private var tempSelectedId: String = ""
    private var tempStartDate: Timestamp? = null

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubcscriptionBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("status", "halo")

        initListView()
        onSubscriptionAdded()
        setupFab()
        swipeRefresh()
        setupObserver()
        setupCancel()
        setupPause()
        setupStart()
        setupRequest()
    }

    private fun initListView() {
        binding.subscriptionRecycler.adapter = adapter
    }

    private fun setupRequest() {
        lifecycleScope.launch {
            viewModel.userId.collect { id ->
                if (!id.isNullOrBlank()) {
                    Log.e("status", "ada")
                    viewModel.getSubscription(id)
                }
            }
        }
    }

    private fun setupFab(){
        binding.fabAdd.setOnClickListener{
            val intent = Intent(requireContext(), SubscriptionFormActivity::class.java)
            launcher.launch(intent)
        }
    }

    private fun swipeRefresh() {
        binding.swiperRefreshSubscription.setOnRefreshListener {
            setupRequest()
            binding.swiperRefreshSubscription.isRefreshing = true
        }
    }

    private fun setupObserver() {
        viewModel.getSubscriptionState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                }

                is Status.SuccessWithData<*> -> {
                    val subscriptions = result.data as List<SubscriptionWithMenu>
                    adapter.items = subscriptions
                    binding.swiperRefreshSubscription.isRefreshing = false
                }
                is Status.Failure -> {
                    Log.e("error", result.message)
                }

                else -> {
                }
            }
        }

        viewModel.cancelSubscriptionState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                }

                is Status.Success -> {
                    Toast.makeText(requireContext(), "Plan Cancelled!!", Toast.LENGTH_SHORT).show()
                    setupRequest()
                }

                else -> {
                }
            }
        }

        viewModel.togglePauseSubscriptionState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                }

                is Status.Success -> {
                    Toast.makeText(requireContext(), "Success!!", Toast.LENGTH_SHORT).show()
                    setupRequest()
                }

                else -> {
                }
            }
        }
    }

    override fun onToogleClick(item: SubscriptionWithMenu) {
        if (item.subscription.status.uppercase() == "ACTIVE"){
            pauseModal.show(parentFragmentManager, "pause")
        }else{
            startModal.show(parentFragmentManager, "start")
        }
        tempSelectedId = item.subscription.id
        tempStartDate = item.subscription.pause_period_start
    }

    override fun onCancelClick(item: SubscriptionWithMenu) {
        cancelModal.show(parentFragmentManager, "cancel")
        tempSelectedId = item.subscription.id
    }


    private fun setupPause(){
        parentFragmentManager.setFragmentResultListener("pauseResult", this) {_, bundle ->
            val startDate = bundle.getLong("startDate", -1L)
            val endDate = bundle.getLong("endDate", -1L)

            viewModel.togglePauseSubsciption(tempSelectedId, Timestamp(Date(startDate)),
                Timestamp(Date(endDate)), true)
        }
    }

    private fun setupStart(){
        parentFragmentManager.setFragmentResultListener("startResult", this) {_, bundle ->
            val result = bundle.getBoolean("start")
            if (result){
                val currentDate = Date()
                viewModel.togglePauseSubsciption(tempSelectedId, tempStartDate!! ,
                    Timestamp(currentDate), false)
            }
        }
    }

    private fun setupCancel(){
        parentFragmentManager.setFragmentResultListener("cancelResult", this) {_, bundle ->
            val result = bundle.getBoolean("cancel")
            if (result){
                val currentDate = Date()
                val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val formattedDate = formatter.format(currentDate)
                viewModel.cancelSubsciption(tempSelectedId, formattedDate)
            }
        }
    }

    private fun onSubscriptionAdded(){
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                setupRequest()
            }
        }
    }


}