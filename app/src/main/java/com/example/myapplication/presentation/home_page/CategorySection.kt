package com.example.myapplication.presentation.home_page

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class Category(val name: String, val icon: ImageVector)

@Composable
fun CategoryGrid(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit
) {
    val rows = (categories.size + 3) / 4 // 3 columns
    val itemHeight = 110.dp
    val gridHeight = itemHeight * rows // ✅ use Dp.times(Int)

    Text(
        "Our Categories",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(16.dp)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight)
            .padding(vertical = 8.dp),
        userScrollEnabled = false // prevent nested scroll crash
    ) {
        items(categories) { category ->
            CategoryItem(category = category) {
                onCategoryClick(category)
            }
        }
    }
}


@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = category.icon,
            contentDescription = category.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(48.dp)
                .padding(top = 8.dp)
        )
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(8.dp)
        )
    }
}
