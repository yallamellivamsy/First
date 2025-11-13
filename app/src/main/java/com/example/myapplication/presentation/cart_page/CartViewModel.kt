package com.example.myapplication.presentation.cart_page

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        val current = _cartItems.value.toMutableList()
        val existing = current.find { it.product.id == product.id }

        if (existing != null) {
            val updated = existing.copy(quantity = existing.quantity + 1)
            current[current.indexOf(existing)] = updated
        } else {
            current.add(CartItem(product, 1))
        }
        _cartItems.value = current
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filterNot { it.product.id == productId }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.product.id == productId) it.copy(quantity = newQuantity) else it
        }
    }
}

data class CartItem(
    val product: Product,
    val quantity: Int
)
