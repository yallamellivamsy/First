package com.example.myapplication.presentation.cart_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CartScreen(cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    LazyColumn(Modifier.fillMaxSize()) {
        items(cartItems) { item ->
            CartItemCard(item, cartViewModel)
            //Text("${item.product.name} - Qty: ${item.quantity}")
        }
        item{
            val totalAmount = cartItems.sumOf { it.product.price * it.quantity }

            Text(
                "Total: ₹$totalAmount",
                modifier = Modifier
                    .padding(16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        item {
            Text(
                "Apply Coupon",
                modifier = Modifier
                    .padding(16.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }

        // Checkout button section
        item {
            Button(
                onClick = { /* checkout logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Proceed to Checkout", Modifier.clickable {

                })
            }
        }

    }

}

@Composable
fun CartItemCard(item: CartItem, cartViewModel: CartViewModel) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.product.imageUrl),
                contentDescription = item.product.description,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFF2F2F2), RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.product.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text("₹${item.product.price}", color = Color.Gray, fontSize = 14.sp)
//                QuantitySelector()
                QuantitySelector(
                    quantity = item.quantity,
                    onIncrease = { cartViewModel.addToCart(item.product) },
                    onDecrease = { cartViewModel.removeFromCart(item.product) }
                )
            }

        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {
        IconButton(
            onClick = onDecrease,
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFFF0F0F0))
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease")
        }

        Text(quantity.toString(), fontWeight = FontWeight.Bold)

        IconButton(
            onClick = onIncrease,
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFFF0F0F0))
        ) {
            Icon(Icons.Default.Add, contentDescription = "Increase")
        }
    }
}