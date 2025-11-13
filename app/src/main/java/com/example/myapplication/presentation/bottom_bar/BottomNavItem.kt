package com.example.myapplication.presentation.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Shop : BottomNavItem("shop", "Shop", Icons.Default.Search)
    object Cart : BottomNavItem("cart", "Cart", Icons.Default.ShoppingCart)
    object Favourites : BottomNavItem("favourites", "Favourites", Icons.Default.Favorite)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
}