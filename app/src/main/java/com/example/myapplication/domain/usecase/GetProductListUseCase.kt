package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.Product
import com.example.myapplication.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> = repository.getProducts()
    suspend fun getPopularProducts(): List<Product> = repository.getPopularProducts()
}