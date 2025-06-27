package com.example.seacatering.ui.Subscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription
import com.example.seacatering.domain.usecase.CreateSubscriptionUseCase
import com.example.seacatering.domain.usecase.GetAllMenuUseCase
import com.example.seacatering.domain.usecase.GetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionFormViewModel @Inject constructor(
    private val createSubscriptionUseCase: CreateSubscriptionUseCase,
    private val getAllMenuUseCase: GetAllMenuUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : ViewModel() {
    private val _createSubscriptionState = MutableLiveData<Status>()
    val createSubscriptionState: LiveData<Status> = _createSubscriptionState

    fun createSubscription(subscription: Subscription){
        viewModelScope.launch{
            _createSubscriptionState.value = Status.Loading

            val result = createSubscriptionUseCase(subscription)

            _createSubscriptionState.value = result
        }
    }

    private val _getAllMenuState = MutableLiveData<Status>()
    val getAllMenuState: LiveData<Status> = _getAllMenuState

    fun getAllMenu(){
        viewModelScope.launch{
            _getAllMenuState.value = Status.Loading

            val result = getAllMenuUseCase()

            _getAllMenuState.value = result
        }
    }

    val userId: StateFlow<String?> = getUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
}