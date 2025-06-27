package com.example.seacatering.ui.auth

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.seacatering.R
import com.example.seacatering.databinding.FragmentAuthRegisterBinding
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthRegisterFragment : Fragment() {
    private var _binding: FragmentAuthRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthRegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthRegisterBinding.inflate(inflater, container, false)
        val view =  binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextWatcher()
        setupForm()
        setupObserve()
        setupToLogin()
    }
    private fun setupForm() {
        binding.registerButton.setOnClickListener {
            if (!checkingValidate()) {
                validateForm()
            } else {
                setupRegisterRequest()
            }
        }
    }

    private fun setupRegisterRequest() {
        viewModel.register(
            binding.registerInputEmail.text.toString(),
            binding.registerInputPass.text.toString()
        )
    }

    private fun setupObserve(){
        viewModel.registerState.observe(viewLifecycleOwner) {result ->
            when (result) {
                is Status.Failure -> {
                    Toast.makeText(requireContext(), "Register failed!!", Toast.LENGTH_SHORT).apply {
                        view?.setBackgroundColor(Color.RED)
                        view?.findViewById<TextView>(android.R.id.message)?.setTextColor(Color.WHITE)
                        show()
                    }
                }
                is Status.Success -> {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid
                    val user = User(uid.toString(), binding.registerInputFullName.text.toString(), binding.registerInputEmail.text.toString())

                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    Log.e("savedID", userId.toString())
                    viewModel.saveUserId(userId.toString())

                    viewModel.createUser(user)
                }
                is Status.Loading -> {}
                else -> {}
            }
        }

        viewModel.createUserState.observe(viewLifecycleOwner) {result ->
            when (result) {
                is Status.Failure -> {
                    Log.e("GAGAL", result.message)
                }
                is Status.Success -> {
                    Toast.makeText(requireContext(), "Register success!!", Toast.LENGTH_SHORT).apply {
                        view?.setBackgroundColor(Color.GREEN)
                        view?.findViewById<TextView>(android.R.id.message)?.setTextColor(Color.WHITE)
                        show()
                    }
                    requireActivity().finish()
                    findNavController().navigate(R.id.action_authRegisterFragment_to_mainActivity)
                }
                is Status.Loading -> {
                    Log.e("LOAD", "LOADING")
                }
                else -> {}
            }
        }
    }

    private fun initTextWatcher() {
        textWatcherFullName()
        textWatcherEmail()
        textWatcherPassword()
    }

    private fun checkingValidate(): Boolean {
        return !(!validateFullName() || !validateEmail() || !validatePassword())
    }


    private fun validateForm() {
        validateFullName()
        validateEmail()
        validatePassword()
    }

    private fun textWatcherFullName() {
        binding.registerInputFullName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validateFullName()
            }

        })
    }

    private fun textWatcherEmail() {
        binding.registerInputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validateEmail()
            }

        })
    }

    private fun textWatcherPassword() {
        binding.registerInputPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validatePassword()
            }

        })
    }

    private fun validateFullName(): Boolean {
        return if (binding.registerInputFullName.length() == 0) {
            errorNullFullName()
            false
        } else {
            clearFullName()
            true
        }
    }

    private fun validateEmail(): Boolean {
        return if (binding.registerInputEmail.length() == 0) {
            errorNullEmail()
            false
        } else if (Patterns.EMAIL_ADDRESS.matcher(binding.registerInputEmail.toString()).matches()) {
            errorEmailFormat()
            false
        } else {
            clearEmail()
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (binding.registerInputPass.length() == 0) {
            errorNullPassword()
            false
        } else {
            clearPassword()
            true
        }
    }

    private fun errorNullFullName() {
        binding.registerFullName.error = "* Nama harus diisi"
        errorBorderFullName()
    }

    private fun errorNullEmail() {
        binding.registerEmail.error = "* Surel harus diisi"
        errorBorderEmail()
    }

    private fun errorEmailFormat() {
        binding.registerEmail.error = "* Format email harus benar"
        errorBorderEmail()
    }

    private fun errorNullPassword() {
        binding.registerPass.error = "* Password harus diisi"
        errorBorderPassword()
    }

    private fun clearFullName() {
        binding.registerFullName.isErrorEnabled = false
        defaultBorderFullName()
    }

    private fun clearEmail() {
        binding.registerEmail.isErrorEnabled = false
        defaultBorderEmail()
    }

    private fun clearPassword() {
        binding.registerPass.isErrorEnabled = false
        defaultBorderPassword()
    }

    private fun defaultBorderFullName() {
        binding.registerInputFullName.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun errorBorderFullName() {
        binding.registerInputFullName.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun defaultBorderEmail() {
        binding.registerInputEmail.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderPassword() {
        binding.registerInputPass.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun errorBorderEmail() {
        binding.registerInputEmail.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderPassword() {
        binding.registerInputPass.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun setupToLogin(){
        binding.registerNavigateToLogin.setOnClickListener{
            findNavController().navigate(R.id.action_authRegisterFragment_to_authLoginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}