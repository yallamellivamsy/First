package com.example.myapplication.data.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val rating: Float,
    val isFavourite: Boolean = false,
    val isAddedToCart: Boolean = false
)