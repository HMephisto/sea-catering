package com.example.seacatering.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.usecase.GetTestimonialsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTestimonialsUseCase: GetTestimonialsUseCase
) : ViewModel() {
    private val _getTestimonialsState = MutableLiveData<Status>()
    val getTestimonialsState: LiveData<Status> = _getTestimonialsState

    fun getTestimonials(){
        viewModelScope.launch{
            _getTestimonialsState.value = Status.Loading

            val result = getTestimonialsUseCase()

            _getTestimonialsState.value = result
        }
    }
}