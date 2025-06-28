package com.example.seacatering.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.usecase.GetActiveSubscriptionsUseCase
import com.example.seacatering.domain.usecase.GetAllSubscriptionsUseCase
import com.example.seacatering.domain.usecase.GetNewSubscriptionDataUseCase
import com.example.seacatering.domain.usecase.GetReactivationUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminStatisticViewModel @Inject constructor(
    private val getNewSubscriptionDataUseCase: GetNewSubscriptionDataUseCase,
    private val getReactivationUseCase: GetReactivationUseCase,
    private val getActiveSubscriptionsUseCase: GetActiveSubscriptionsUseCase
) : ViewModel() {
    private val _getNewSubscriptionsDataState = MutableLiveData<Status>()
    val getNewSubscriptionsDataState: LiveData<Status> = _getNewSubscriptionsDataState

    private val _getReactivationState = MutableLiveData<Status>()
    val getReactivationState: LiveData<Status> = _getReactivationState

    private val _getActiveSubscriptionState = MutableLiveData<Status>()
    val getActiveSubscriptionState: LiveData<Status> = _getActiveSubscriptionState


    fun getActiveSubscriptionsData(){
        viewModelScope.launch{
            _getActiveSubscriptionState.value = Status.Loading

            val result = getActiveSubscriptionsUseCase()

            _getActiveSubscriptionState.value = result
        }
    }


    fun getNewSubscriptionsData(startDate: Timestamp, endDate: Timestamp){
        viewModelScope.launch{
            _getNewSubscriptionsDataState.value = Status.Loading

            val result = getNewSubscriptionDataUseCase(startDate, endDate)

            _getNewSubscriptionsDataState.value = result
        }
    }

    fun getReactivation(startDate: Timestamp, endDate: Timestamp){
        viewModelScope.launch{
            _getReactivationState.value = Status.Loading

            val result = getReactivationUseCase(startDate, endDate)

            _getReactivationState.value = result
        }
    }
}