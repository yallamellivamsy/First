package com.example.myapplication.presentation.home_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.model.Product


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onCategoryClick: (Category) -> Unit, popularProducts: List<Product>, onPopularClick: (Product) -> Unit) {

    val categories = listOf(
        Category("Pickles", Icons.Default.LocalDining),
        Category("Powders", Icons.Default.EmojiFoodBeverage),
        Category("Snacks", Icons.Default.Fastfood),
        Category("Oils", Icons.Default.LocalMall),
        Category("Drinks", Icons.Default.LocalCafe),
        Category("Masalas", Icons.Default.LocalFireDepartment),
        Category("Healthy", Icons.Default.Eco),
        Category("More", Icons.Default.MoreHoriz)
    )

    val recentlyViewed = listOf(
        ImageBitmap.imageResource(id = R.mipmap.img),
        ImageBitmap.imageResource(id = R.mipmap.img_1),
        ImageBitmap.imageResource(id = R.mipmap.img),
        ImageBitmap.imageResource(id = R.mipmap.img_1),
        ImageBitmap.imageResource(id = R.mipmap.img),
        ImageBitmap.imageResource(id = R.mipmap.img_1),
        ImageBitmap.imageResource(id = R.mipmap.img),
        ImageBitmap.imageResource(id = R.mipmap.img_1),
        ImageBitmap.imageResource(id = R.mipmap.img)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MyService", fontWeight = FontWeight.Bold) }
            )
        }
    ) {
        padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 0.dp)
        ) {
            AccountSection()
            Spacer(Modifier.height(16.dp))

            SearchBarSection()
            Spacer(Modifier.height(16.dp))

            CarouselSection()
            Spacer(Modifier.height(24.dp))

            PopularSection(popularProducts, onPopularClick)
            Spacer(Modifier.height(0.dp))

//            if(!recentlyViewed.isEmpty()){
                RecentlyViewed(recentlyViewed)
                Spacer(Modifier.height(0.dp))
//            }

            CategoryGrid(
                categories = categories,
                onCategoryClick = onCategoryClick
            )
        }
    }
}


