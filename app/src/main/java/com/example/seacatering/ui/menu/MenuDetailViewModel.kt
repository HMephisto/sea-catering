package com.example.seacatering.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.usecase.GetMenuDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuDetailViewModel @Inject constructor(
    private val getMenuDetailUseCase: GetMenuDetailUseCase
) : ViewModel() {
    private val _getMenuDetailState = MutableLiveData<Status>()
    val getMenuDetailState : LiveData<Status> = _getMenuDetailState

    fun getMenuDetail(menuId: String){
        viewModelScope.launch{
            _getMenuDetailState.value = Status.Loading

            val result = getMenuDetailUseCase(menuId)

            _getMenuDetailState.value = result
        }
    }

}