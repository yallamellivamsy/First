package com.example.myapplication.presentation.payments

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun PaymentResultObserver(paymentViewModel: PaymentViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        paymentViewModel.events.collect { event ->
            when (event) {
                is PaymentEvent.Success -> {
                    Toast.makeText(context, "Payment Success: ${event.paymentId}", Toast.LENGTH_LONG).show()
                }

                is PaymentEvent.Error -> {
                    Toast.makeText(context, "Payment Failed: ${event.error}", Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }
}