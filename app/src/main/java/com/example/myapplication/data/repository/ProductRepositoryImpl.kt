package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Product
import com.example.myapplication.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor() : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        // later this can come from API or local DB

        return listOf(
            Product(1, "Mango Pickle", "Homemade spicy mango pickle", 250.0, "https://picsum.photos/200", 4f ),
            Product(2, "Turmeric Powder", "Organic turmeric powder", 120.0, "https://picsum.photos/201", 4.5f),
            Product(3, "Curry Leaves Powder", "Healthy homemade curry leaves mix", 180.0, "https://picsum.photos/202", 4.5f),
            Product(4, "Lemon Pickle", "Tangy and spicy lemon pickle made with pure oil", 230.0, "https://picsum.photos/203", 4f),
            Product(5, "Ginger Garlic Paste", "Ready-to-use homemade ginger garlic paste", 150.0, "https://picsum.photos/204", 3f),
            Product(6, "Red Chilli Powder", "Pure and vibrant homemade red chilli powder", 130.0, "https://picsum.photos/205", 4f),
            Product(7, "Sambar Powder", "Traditional South Indian sambar masala", 160.0, "https://picsum.photos/206", 4f),
            Product(8, "Rasam Powder", "Aromatic rasam powder blend with pepper and cumin", 140.0, "https://picsum.photos/207", 4f),
            Product(9, "Coriander Powder", "Freshly ground homemade coriander powder", 110.0, "https://picsum.photos/208", 4f),
            Product(10, "Coconut Chutney Powder", "Tasty and healthy dry chutney mix", 190.0, "https://picsum.photos/209", 4f),
            Product(11, "Tomato Pickle", "Spicy and tangy tomato pickle with garlic flavor", 260.0, "https://picsum.photos/210", 4f),
            Product(12, "Peanut Chutney Powder", "Protein-rich homemade peanut chutney mix", 170.0, "https://picsum.photos/211", 4f),
            Product(13, "Dry Fish Pickle", "Traditional coastal-style dry fish pickle", 350.0, "https://picsum.photos/212", 4f),
            Product(14, "Garlic Pickle", "Rich garlic pickle in mustard oil", 240.0, "https://picsum.photos/213", 4f),
            Product(15, "Idli Podi", "Tasty gunpowder mix for idli & dosa with sesame flavor", 200.0, "https://picsum.photos/214", 4f)
        )

    }
}