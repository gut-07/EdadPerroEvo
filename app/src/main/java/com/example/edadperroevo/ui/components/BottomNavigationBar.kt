package com.example.edadperroevo.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.edadperroevo.navigation.Screen

sealed class BottomBarItem(val route: String, val title: String, val icon: ImageVector) {
    object Calculadora : BottomBarItem(Screen.Calculadora.route, "Calculadora", Icons.Default.Calculate)
    object Marketplace : BottomBarItem(Screen.Marketplace.route, "Tienda", Icons.Default.Store)
    object ChatIA : BottomBarItem(Screen.ChatIA.route, "Asesor IA", Icons.Default.SmartToy)
    object ChatUsuarios : BottomBarItem(Screen.ChatUsuarios.route, "Comunidad", Icons.Default.Chat)
    object Mapa : BottomBarItem(Screen.Mapa.route, "Ubicaciones", Icons.Default.Map)
    object Perfil : BottomBarItem(Screen.Perfil.route, "Perfil", Icons.Default.Person)
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomBarItem.Calculadora,
        BottomBarItem.Marketplace,
        BottomBarItem.ChatIA,
        BottomBarItem.ChatUsuarios,
        BottomBarItem.Mapa,
        BottomBarItem.Perfil
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelSmall // Estilo compacto para evitar saltos de línea
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}