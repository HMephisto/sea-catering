package com.example.seacatering.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.usecase.GetAllSubscriptionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminSubscriptionViewModel @Inject constructor(
    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase
) : ViewModel() {
    private val _getAllSubscriptionState = MutableLiveData<Status>()
    val getAllSubscriptionState: LiveData<Status> = _getAllSubscriptionState

    fun getAllSubscriptions(status: String){
        viewModelScope.launch{
            _getAllSubscriptionState.value = Status.Loading

            val result = getAllSubscriptionsUseCase(status)

            _getAllSubscriptionState.value = result
        }
    }
}