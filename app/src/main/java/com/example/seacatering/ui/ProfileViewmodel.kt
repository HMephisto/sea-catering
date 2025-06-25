package com.example.seacatering.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.usecase.LogoutUseCase
import com.example.seacatering.domain.usecase.SaveUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor (
    private val logoutUseCase: LogoutUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase
) : ViewModel() {
    private val _logoutState = MutableLiveData<Status>()
    val logoutState: LiveData<Status> = _logoutState

    fun logout(){
        viewModelScope.launch{
            _logoutState.value = Status.Loading

            val result = logoutUseCase()

            _logoutState.value = result
        }
    }

    fun saveUserId(userId: String) {
        viewModelScope.launch {
            saveUserIdUseCase(userId)
        }
    }
}