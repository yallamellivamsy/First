package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.presentation.bottom_bar.BottomTabNavigation
import com.example.myapplication.presentation.payments.PaymentViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.presentation.payments.PaymentEvent
import com.razorpay.Checkout
import kotlinx.coroutines.launch
import org.json.JSONObject


@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {
    private val paymentViewModel: PaymentViewModel by viewModels()

    //@RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observePaymentEvents()

        enableEdgeToEdge()

        setContent {
            BottomTabNavigation(paymentViewModel = paymentViewModel)
        }
    }

    private fun observePaymentEvents() {
        lifecycleScope.launch {
            paymentViewModel.events.collect { event ->
                when (event) {
                    is PaymentEvent.StartPayment -> {
                        startRazorpayPayment(event.amount)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun startRazorpayPayment(amount: Double) {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_RhcdpO4pSia6ai")   // Use your test key here

        val options = JSONObject().apply {
            put("name", "My Store")
            put("description", "Checkout Payment")
            put("currency", "INR")
            put("amount", (amount * 100).toInt()) // Convert to paise
            //put("order_id", "order_Rhumsri8E1PPn9")
        }
        checkout.open(this, options)
    }

    override fun onPaymentSuccess(paymentId: String?) {
        paymentViewModel.paymentSuccess(paymentId ?: "")
    }

    override fun onPaymentError(code: Int, description: String?) {
        paymentViewModel.paymentError(description ?: "Unknown Error")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}