package com.example.composetests.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class ObservableViewModel: ViewModel() {
    private val _liveData = MutableLiveData("Hello World")
    val liveData: LiveData<String> = _liveData

    private val _stateFlow = MutableStateFlow("Hello World")
    val stateFlow = _stateFlow.asStateFlow()

    private val _shareFlow = MutableSharedFlow<String>()
    val shareFlow = _shareFlow.asSharedFlow()

    fun triggerLiveData() {
        _liveData.value = "Live Data"
    }

    fun triggerStateFlow() {
        _stateFlow.value = "State Flow"
    }

    fun triggerFlow(): Flow<String> {
        return flow {
            repeat(5){
                emit("Item $it")
                kotlinx.coroutines.delay(1000L)
            }
        }

    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            _shareFlow.emit("Shared Flow")
        }
    }


}