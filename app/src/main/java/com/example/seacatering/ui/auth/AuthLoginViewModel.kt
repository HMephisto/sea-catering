package com.example.seacatering.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthLoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _authState = MutableLiveData<Status>()
    val authState: LiveData<Status> = _authState

    fun login(email: String, password: String){
        viewModelScope.launch{
            _authState.value = Status.Loading

            val result = loginUseCase(email, password)

            _authState.value = result
        }
    }
}