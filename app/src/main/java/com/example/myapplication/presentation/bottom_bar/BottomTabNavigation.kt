package com.example.myapplication.presentation.bottom_bar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.cart_page.CartScreen
import com.example.myapplication.presentation.cart_page.CartViewModel
import com.example.myapplication.presentation.home_page.HomeScreen
import com.example.myapplication.presentation.payments.PaymentViewModel
import com.example.myapplication.presentation.productdetailpage.ProductDetailScreen
import com.example.myapplication.presentation.productlist.ProductListScreen
import com.example.myapplication.presentation.productlist.ProductListViewModel

@Composable
fun BottomTabNavigation(
    paymentViewModel: PaymentViewModel,
    viewModel: ProductListViewModel = hiltViewModel()

) {
    val navController = rememberNavController()

    val products by viewModel.productList.collectAsState()
    val popularProducts by viewModel.popularProductList.collectAsState()

    val cartViewModel: CartViewModel = hiltViewModel()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Shop,
        BottomNavItem.Cart,
        BottomNavItem.Favourites,
        BottomNavItem.Profile
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val isCartSelected = currentRoute == BottomNavItem.Cart.route

    // 🔹 Animate FAB size and color
    val fabSize by animateDpAsState(
        targetValue = if (isCartSelected) 60.dp else 50.dp,
        label = "FAB Size"
    )
    val fabColor by animateColorAsState(
        targetValue = if (isCartSelected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.secondaryContainer,
        label = "FAB Color"
    )

    Scaffold(
        bottomBar = {
            BottomBar(navController, items)
        },
        floatingActionButton = {
            CustomFAB(navController, fabSize, fabColor )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    onCategoryClick = { category ->
                        navController.navigate("home/product_list/${category.name}")
                    },
                    popularProducts = popularProducts,
                    onPopularClick = { product ->
                        navController.navigate("home/product_detail/${product.id}")
                    }
                )
            }

            composable(
                "home/product_list/{category}",
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                ProductListScreen(
                    products = products,
                    onProductClick = { product ->
                        navController.navigate("home/product_detail/${product.id}")
                    },
                    navController,
                    cartViewModel
                )
            }

            composable(
                route = "home/product_detail/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                val product = products.firstOrNull { it.id == productId }

                product?.let {
                    ProductDetailScreen(
                        product = it,
                        onAddToCart = { /*viewModel.addToCart(it)*/ }, // optional
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }


            composable(BottomNavItem.Favourites.route) { CategoriesScreen() }
            composable(BottomNavItem.Cart.route) { CartScreen(cartViewModel, paymentViewModel) }
            composable(BottomNavItem.Shop.route) { OrdersScreen() }
            composable(BottomNavItem.Profile.route) { ProfileScreen() }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, items: List<BottomNavItem>) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Surface(
        tonalElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentRoute?.startsWith(item.route)
                if (selected != null) {
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (selected) {
                                // If already on that tab, pop back to its root screen
                                navController.popBackStack(item.route, inclusive = false)
                            }else {
                                navController.navigate(item.route) {
                                    // Avoid building multiple copies of the same destination
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(26.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomFAB(navController: NavHostController, fabSize: Dp, fabColor:Color) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    FloatingActionButton(
        onClick = {
            if (currentRoute != BottomNavItem.Cart.route) {
                navController.navigate(BottomNavItem.Cart.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            } else {
                navController.popBackStack(BottomNavItem.Cart.route, inclusive = false)
            }
        },
        modifier = Modifier
            .size(fabSize)
            .offset(y = 50.dp),
        containerColor = fabColor,
        contentColor = Color.White
    ) {
        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResponsiveBottomTabs() {
    MaterialTheme {
        BottomTabNavigation(paymentViewModel = hiltViewModel())
    }
}
