package com.example.myapplication.presentation.productlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.data.model.Product
import androidx.compose.foundation.lazy.items
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.ui.platform.LocalContext
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(viewModel: ProductListViewModel = hiltViewModel()) {
    val products by viewModel.productList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Our Products") })
        }
    ) { padding ->
//        LazyColumn(contentPadding = padding) {
//            items(products) { product ->
//                //ProductItem(product)
//                ProductItem(
//                    product = product,
//                    onAddToCart = { /* handle add */ },
//                    onIncrease = { /* handle increase */ },
//                    onDecrease = { /* handle decrease */ }
//                )
//            }
//        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // ✅ 2 items per row
            contentPadding = PaddingValues(8.dp), // Optional padding
            modifier = Modifier.padding(padding)
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onAddToCart = { /* handle add */ },
                    onIncrease = { /* handle increase */ },
                    onDecrease = { /* handle decrease */ }
                )
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(product.imageUrl)
            .crossfade(true)
            .listener(
                onError = { _, error -> Log.e("Coil", "Image load failed", error.throwable) },
                onSuccess = { _, _ -> Log.d("Coil", "Image loaded successfully") }
            )
            .build()
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {

            Image(
                painter = painter,
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(product.name, fontWeight = FontWeight.Bold)
                Text(product.description, style = MaterialTheme.typography.bodySmall)
                Text("₹${product.price}", color = Color(0xFF388E3C))
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onAddToCart: (Product) -> Unit,
    onIncrease: (Product) -> Unit,
    onDecrease: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableStateOf(0) }

    var isFavourite by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .width(160.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🖼 Product Image + ❤️ Favourite Icon Overlay
            Box(
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
            ) {
                // Product Image
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.imageUrl)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                // ❤️ Favourite Icon
                IconButton(
                    onClick = { isFavourite = !isFavourite },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(28.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.7f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Add to favourites",
                        tint = if (isFavourite) Color.Red else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🏷 Product name
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
            )

            // 💸 Price
            Text(
                text = "₹${product.price}",
                color = Color(0xFF388E3C),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )

            // ⭐ Rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${product.rating}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // 🛒 Add / Quantity Controls
            if (quantity == 0) {
                Button(
                    onClick = {
                        quantity = 1
                        onAddToCart(product)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                ) {
                    Text("Add to Cart")
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 8.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (quantity > 0) {
                                quantity--
                                onDecrease(product)
                            }
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Text(
                        text = quantity.toString(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    IconButton(
                        onClick = {
                            quantity++
                            onIncrease(product)
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }

    }
}

