package com.example.seacatering.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.usecase.GetAllMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getAllMenuUseCase: GetAllMenuUseCase
) : ViewModel() {
    private val _getAllMenuState = MutableLiveData<Status>()
    val getAllMenuState: LiveData<Status> = _getAllMenuState

    fun getAllMenu(){
        viewModelScope.launch{
            _getAllMenuState.value = Status.Loading

            val result = getAllMenuUseCase()

            _getAllMenuState.value = result
        }
    }
}