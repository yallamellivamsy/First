package com.example.myapplication.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Product
import com.example.myapplication.domain.usecase.GetProductListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase
) : ViewModel() {

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList

    private val _popularProductList = MutableStateFlow<List<Product>>(emptyList())
    val popularProductList: StateFlow<List<Product>> = _popularProductList

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _productList.value = getProductListUseCase()
            _popularProductList.value = getProductListUseCase.getPopularProducts()
        }
    }
}
