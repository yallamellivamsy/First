package com.example.myapplication.presentation.home_page

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselSection() {
    val images = listOf(
        "https://picsum.photos/800/400?1",
        "https://picsum.photos/800/400?2",
        "https://picsum.photos/800/400?3"
    )

    val pagerState = rememberPagerState(pageCount = { images.size })

    Column {
        HorizontalPager(state = pagerState, modifier = Modifier.height(180.dp)) { page ->
            Image(
                painter = rememberAsyncImagePainter(images[page]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(8.dp))

        HorizontalPagerIndicatorNew(
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = Color.Gray
        )

    }
}

@Composable
fun HorizontalPagerIndicatorNew(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.Black,
    inactiveColor: Color = Color.LightGray,
    indicatorWidth: Int = 8,
    spacing: Int = 8
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing.dp),
        modifier = modifier
    ) {
        repeat(pagerState.pageCount) { index ->
            val color =
                if (pagerState.currentPage == index) activeColor else inactiveColor
            Canvas(
                modifier = Modifier.size(indicatorWidth.dp)
            ) {
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(50f, 50f)
                )
            }
        }
    }
}
