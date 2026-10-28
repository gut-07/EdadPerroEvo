package com.example.edadperroevo.ui.screens

import androidx.compose.runtime.mutableStateListOf

data class ProductoAlimento(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val descripcion: String
)

object CarritoState {
    val itemsCarrito = mutableStateListOf<Pair<ProductoAlimento, Int>>()
}