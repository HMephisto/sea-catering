package com.example.seacatering.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Testimony
import com.example.seacatering.domain.usecase.CreateTestimonyUseCase
import com.example.seacatering.domain.usecase.GetUserDataUseCase
import com.example.seacatering.domain.usecase.GetUserIdUseCase
import com.example.seacatering.domain.usecase.LogoutUseCase
import com.example.seacatering.domain.usecase.SaveUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor (
    private val logoutUseCase: LogoutUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val createTestimonyUseCase: CreateTestimonyUseCase,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {
    private val _logoutState = MutableLiveData<Status>()
    val logoutState: LiveData<Status> = _logoutState

    private val _createTestimonyState = MutableLiveData<Status>()
    val createTestimonyState: LiveData<Status> = _createTestimonyState

    private val _getUserDataState = MutableLiveData<Status>()
    val getUserDataState: LiveData<Status> = _getUserDataState

    fun logout(){
        viewModelScope.launch{
            _logoutState.value = Status.Loading

            val result = logoutUseCase()

            _logoutState.value = result
        }
    }

    fun getUserData(userId: String){
        viewModelScope.launch{
            _getUserDataState.value = Status.Loading

            val result = getUserDataUseCase(userId)

            _getUserDataState.value = result
        }
    }

    val userId: StateFlow<String?> = getUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun saveUserId(userId: String) {
        viewModelScope.launch {
            saveUserIdUseCase(userId)
        }
    }

    fun createTestimony(testimony: Testimony){
        viewModelScope.launch{
            _createTestimonyState.value = Status.Loading

            val result = createTestimonyUseCase(testimony)

            _createTestimonyState.value = result
        }
    }
}