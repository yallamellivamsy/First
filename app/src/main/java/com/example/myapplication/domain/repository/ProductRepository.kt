package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
}