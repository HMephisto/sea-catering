package com.example.seacatering.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.User
import com.example.seacatering.domain.usecase.CreateUserUseCase
import com.example.seacatering.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel(){
    private val _registerState = MutableLiveData<Status>()
    val registerState: LiveData<Status> = _registerState

    private val _createUserState = MutableLiveData<Status>()
    val createUserState: LiveData<Status> = _createUserState

    fun register(email: String, password: String){
        viewModelScope.launch{
            _registerState.value = Status.Loading

            val result = registerUseCase(email, password)

            _registerState.value = result
        }
    }

    fun createUser(user: User){
        viewModelScope.launch{
            _createUserState.value = Status.Loading

            val result = createUserUseCase(user)

            _createUserState.value = result
        }
    }
}