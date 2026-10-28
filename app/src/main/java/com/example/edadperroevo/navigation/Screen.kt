package com.example.edadperroevo.navigation

sealed class Screen(val route: String) {
    object Calculadora : Screen("calculadora")
    object Marketplace : Screen("marketplace")
    object Carrito : Screen("carrito")
    object ChatIA : Screen("chat_ia")
    object ChatUsuarios : Screen("chat_usuarios")
    object Mapa : Screen("mapa")
    object Perfil : Screen("perfil")
}