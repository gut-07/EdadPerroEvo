package com.example.edadperroevo.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.edadperroevo.navigation.Screen
import com.example.edadperroevo.ui.components.BottomNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        SplashScreen(onTimeout = { showSplash = false })
    } else {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Calculadora.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Calculadora.route) { CalculadoraScreen() }
                composable(Screen.Marketplace.route) {
                    MarketplaceScreen(onNavigateToCarrito = { navController.navigate(Screen.Carrito.route) })
                }
                composable(Screen.Carrito.route) {
                    CarritoScreen(onNavigateToMarketplace = {
                        navController.navigate(Screen.Marketplace.route) {
                            popUpTo(Screen.Marketplace.route) { inclusive = true }
                        }
                    })
                }
                composable(Screen.ChatIA.route) { ChatIAScreen() }
                composable(Screen.ChatUsuarios.route) { ChatScreen() }
                composable(Screen.Mapa.route) { MapScreen() }
                composable(Screen.Perfil.route) { PerfilScreen() }
            }
        }
    }
}