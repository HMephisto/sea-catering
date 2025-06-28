package com.example.seacatering.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.usecase.GetUserDataUseCase
import com.example.seacatering.domain.usecase.GetUserIdUseCase
import com.example.seacatering.domain.usecase.LoginUseCase
import com.example.seacatering.domain.usecase.SaveUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthLoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {
    private val _authState = MutableLiveData<Status>()
    val authState: LiveData<Status> = _authState

    private val _getUserDataState = MutableLiveData<Status>()
    val getUserDataState: LiveData<Status> = _getUserDataState

    fun login(email: String, password: String){
        viewModelScope.launch{
            _authState.value = Status.Loading

            val result = loginUseCase(email, password)

            _authState.value = result
        }
    }
    fun saveUserId(userId: String) {
        viewModelScope.launch {
            saveUserIdUseCase(userId)
        }
    }

    val userId: StateFlow<String?> = getUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun getUserData(userId: String){
        viewModelScope.launch{
            _getUserDataState.value = Status.Loading

            val result = getUserDataUseCase(userId)

            _getUserDataState.value = result
        }
    }
}