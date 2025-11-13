package com.example.myapplication.presentation.cart_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import androidx.compose.foundation.lazy.items

@Composable
fun CartScreen(cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    LazyColumn {
        items(cartItems) { item ->
            Text("${item.product.name} - Qty: ${item.quantity}")
        }
    }
}