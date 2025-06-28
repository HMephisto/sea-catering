package com.example.seacatering.ui.Subscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription
import com.example.seacatering.domain.usecase.CancelSubscriptionUseCase
import com.example.seacatering.domain.usecase.CreateSubscriptionUseCase
import com.example.seacatering.domain.usecase.GetAllMenuUseCase
import com.example.seacatering.domain.usecase.GetSubscriptionUseCase
import com.example.seacatering.domain.usecase.GetUserIdUseCase
import com.example.seacatering.domain.usecase.TogglePauseSubscriptionUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val getSubscriptionUseCase: GetSubscriptionUseCase,
    getUserIdUseCase: GetUserIdUseCase,
    private val togglePauseSubscriptionUseCase: TogglePauseSubscriptionUseCase,
    private val cancelSubscriptionUseCase: CancelSubscriptionUseCase
) : ViewModel() {
    private val _getSubscriptionState = MutableLiveData<Status>()
    val getSubscriptionState: LiveData<Status> = _getSubscriptionState
    private val _togglePauseSubscriptionState = MutableLiveData<Status>()
    val togglePauseSubscriptionState: LiveData<Status> = _togglePauseSubscriptionState
    private val _cancelSubscriptionState = MutableLiveData<Status>()
    val cancelSubscriptionState: LiveData<Status> = _cancelSubscriptionState

    fun getSubscription(userId: String){
        viewModelScope.launch{
            _getSubscriptionState.value = Status.Loading

            val result = getSubscriptionUseCase(userId)

            _getSubscriptionState.value = result
        }
    }

    val userId: StateFlow<String?> = getUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun togglePauseSubsciption(subscriptionId:String, startDate: Timestamp, endDate: Timestamp, pause: Boolean){
        viewModelScope.launch{
            _togglePauseSubscriptionState.value = Status.Loading

            val result = togglePauseSubscriptionUseCase(subscriptionId, startDate, endDate,pause)

            _togglePauseSubscriptionState.value = result
        }
    }

    fun cancelSubsciption(subscriptionId:String, endDate: String){
        viewModelScope.launch{
            _cancelSubscriptionState.value = Status.Loading

            val result = cancelSubscriptionUseCase(subscriptionId, endDate)

            _cancelSubscriptionState.value = result
        }
    }

}