package com.example.seacatering.ui


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.seacatering.databinding.FragmentProfileBinding
import com.example.seacatering.domain.model.Status
import com.example.seacatering.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLogoutButton()
    }

    private fun onLogoutButton() {
        viewModel.logout()
        binding.logoutButton.setOnClickListener {
            viewModel.logoutState.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Status.Loading -> {}

                    Status.Success -> {
                        viewModel.saveUserId("")
                        val intent = Intent(this.context, AuthActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }

                    else -> {}
                }
            }
        }
    }
}