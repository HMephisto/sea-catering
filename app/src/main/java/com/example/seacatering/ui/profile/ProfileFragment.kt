package com.example.seacatering.ui.profile


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.seacatering.databinding.FragmentProfileBinding
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Testimony
import com.example.seacatering.domain.model.User
import com.example.seacatering.ui.auth.AuthActivity
import com.example.seacatering.ui.condition.TestimonySubmitionFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.getValue

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewmodel by viewModels()

    private val testimonyModal = TestimonySubmitionFragment()

    private var username = ""
    private var userId = ""

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
        onAddTestimony()
        setupObserver()
        setupTestimony()
        getUserName()
    }

    private fun getUserName(){
        lifecycleScope.launch {
            viewModel.userId.collect { id ->
                if (!id.isNullOrBlank()) {
                    Log.e("data", id)
                    userId = id
                    viewModel.getUserData(userId)
                }
            }
        }
    }

    private fun onAddTestimony() {
        binding.addTestimony.setOnClickListener {
            testimonyModal.show(parentFragmentManager, "testimony")
        }
    }

    private fun setupTestimony() {
        parentFragmentManager.setFragmentResultListener("testimonyResult", this) { _, bundle ->
            val message = bundle.getString("message")
            val rating = bundle.getInt("rating")

            val testimony = Testimony(UUID.randomUUID().toString(), userId, username, message.toString(), rating)
            viewModel.createTestimony(testimony)
        }
    }

    private fun setupObserver(){
        viewModel.getUserDataState.observe(viewLifecycleOwner) {result ->
            when (result){
                is Status.SuccessWithData<*> -> {
                    val user = result.data as User
                    username = user.name
                    binding.username.text = username
                }
                else -> {

                }
            }
        }
        viewModel.createTestimonyState.observe(viewLifecycleOwner) {result ->
            when (result){
                is Status.Success -> {
                    Toast.makeText(requireContext(), "Testimony Added", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
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

    private fun onLogoutButton() {

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
}