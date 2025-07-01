package com.example.seacatering.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.seacatering.R
import com.example.seacatering.databinding.FragmentSplashBinding
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            lifecycleScope.launch {
                var count = 0
                viewModel.userId.collect { id ->
                    count++
                    if (!id.isNullOrBlank()) {
                        viewModel.getUserData(id)
                    } else if (id.isNullOrBlank() && count == 2){
                        findNavController().navigate(R.id.action_splashFragment_to_authLoginFragment)
                    }
            }
        }


        viewModel.getUserDataState.observe(viewLifecycleOwner)
        { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    val user = result.data as User
                    if (user.status == "user") {
                        findNavController().navigate(R.id.action_splashFragment_to_mainActivity)
                        requireActivity().finish()
                    } else {
                        findNavController().navigate(R.id.action_splashFragment_to_adminActivity)
                        requireActivity().finish()
                    }
                }

                is Status.Loading -> {
                }

                else -> {}
            }
        }
    }
}