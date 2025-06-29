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
import androidx.navigation.fragment.findNavController
import com.example.seacatering.R
import com.example.seacatering.databinding.FragmentAuthLoginBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.User
import com.example.seacatering.ui.condition.LoadingCondition
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthLoginFragment : Fragment() {
    private var _binding: FragmentAuthLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthLoginViewModel by viewModels()
    private val dialog = LoadingCondition()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initTextWatcher()
        setupForm()
        setupObserve()
        setupToRegister()
    }

    private fun setupForm() {
        binding.loginButton.setOnClickListener {
            if (!checkingValidate()) {
                validateForm()
            } else {
                setupRequest()
            }
        }
    }

    private fun setupRequest() {
        viewModel.login(
            binding.loginInputEmail.text.toString(),
            binding.loginInputPass.text.toString()
        )
    }

    private fun setupObserve() {
        viewModel.getUserDataState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.SuccessWithData<*> -> {
                    dialog.dismiss()
                    val user = result.data as User
                    if (user.status == "user") {
                        findNavController().navigate(R.id.action_authLoginFragment_to_mainActivity)
                        requireActivity().finish()
                    } else {
                        findNavController().navigate(R.id.action_authLoginFragment_to_adminActivity)
                        requireActivity().finish()
                    }
                }

                is Status.Loading -> {
                    checkExistingDialog()
                }

                else -> {}
            }
        }

        viewModel.authState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Failure -> {
                    dialog.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "Login failed!! Check your email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_authLoginFragment_to_authLoginErrorCondition)
                }

                is Status.Success -> {
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "Login success!!", Toast.LENGTH_SHORT).show()

                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    viewModel.saveUserId(userId.toString())

                    viewModel.getUserData(userId.toString())
                }

                is Status.Loading -> {
                    checkExistingDialog()
                }

                else -> {}
            }
        }
    }

    private fun checkExistingDialog(){
        val existing = parentFragmentManager.findFragmentByTag("MyCustomDialog")
        if (existing == null) {
            dialog.show(parentFragmentManager, "MyCustomDialog")
        }
    }
    private fun initTextWatcher() {
        textWatcherEmail()
        textWatcherPassword()
    }

    private fun checkingValidate(): Boolean {
        return !(!validateEmail() || !validatePassword())
    }


    private fun validateForm() {
        validateEmail()
        validatePassword()
    }

    private fun textWatcherEmail() {
        binding.loginInputEmail.addTextChangedListener(object : TextWatcher {
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
        binding.loginInputPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validatePassword()
            }

        })
    }

    private fun validateEmail(): Boolean {
        return if (binding.loginInputEmail.length() == 0) {
            errorNullEmail()
            false
        } else if (Patterns.EMAIL_ADDRESS.matcher(binding.loginInputEmail.toString()).matches()) {
            errorEmailFormat()
            false
        } else {
            clearEmail()
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (binding.loginInputPass.length() == 0) {
            errorNullPassword()
            false
        } else {
            clearPassword()
            true
        }
    }

    private fun errorNullEmail() {
        binding.loginEmail.error = "* Surel harus diisi"
        errorBorderEmail()
    }

    private fun errorEmailFormat() {
        binding.loginEmail.error = "* Format email harus benar"
        errorBorderEmail()
    }

    private fun errorNullPassword() {
        binding.loginPass.error = "* Password harus diisi"
        errorBorderPassword()
    }

    private fun clearEmail() {
        binding.loginEmail.isErrorEnabled = false
        defaultBorderEmail()
    }

    private fun clearPassword() {
        binding.loginPass.isErrorEnabled = false
        defaultBorderPassword()
    }

    private fun defaultBorderEmail() {
        binding.loginInputEmail.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderPassword() {
        binding.loginInputPass.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun errorBorderEmail() {
        binding.loginInputEmail.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderPassword() {
        binding.loginInputPass.setBackgroundResource(R.drawable.bg_white_red_outline)
    }


    private fun setupToRegister() {
        binding.loginNavigateToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_authLoginFragment_to_authRegisterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}