package com.example.myapplication.presentation.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {

    private val _events = MutableSharedFlow<PaymentEvent>()
    val events = _events.asSharedFlow()

    fun startPayment(amount: Double) {
        viewModelScope.launch {
            _events.emit(PaymentEvent.StartPayment(amount))
        }
    }

    fun paymentSuccess(paymentId: String) {
        viewModelScope.launch {
            _events.emit(PaymentEvent.Success(paymentId))
        }
    }

    fun paymentError(error: String) {
        viewModelScope.launch {
            _events.emit(PaymentEvent.Error(error))
        }
    }
}

sealed class PaymentEvent {
    data class StartPayment(val amount: Double) : PaymentEvent()
    data class Success(val paymentId: String) : PaymentEvent()
    data class Error(val error: String) : PaymentEvent()
}
