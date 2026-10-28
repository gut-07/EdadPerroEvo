package com.example.edadperroevo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(onNavigateToCarrito: () -> Unit) {
    val catalogo = listOf(
        ProductoAlimento(1, "Concentrado Adulto 15kg", "Seco", 120000.0, "Alimento balanceado enriquecido con omega 3."),
        ProductoAlimento(2, "Alimento Húmedo Pollo 300g", "Húmedo", 12500.0, "Lata de carne tierna en salsa."),
        ProductoAlimento(3, "Snacks Dental Stick x7", "Snacks", 18000.0, "Premios masticables para higiene oral."),
        ProductoAlimento(4, "Concentrado Cachorro 8kg", "Seco", 85000.0, "Fórmula especial para crecimiento rápido.")
    )

    var filtroCategoria by remember { mutableStateOf("Todos") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Marketplace Canino") },
                actions = {
                    FilledTonalButton(
                        onClick = onNavigateToCarrito,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("🛒 Carrito (${CarritoState.itemsCarrito.sumOf { it.second }})")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("Todos", "Seco", "Húmedo", "Snacks").forEach { cat ->
                    FilterChip(
                        selected = filtroCategoria == cat,
                        onClick = { filtroCategoria = cat },
                        label = { Text(cat) }
                    )
                }
            }

            val productosFiltrados = if (filtroCategoria == "Todos") catalogo else catalogo.filter { it.categoria == filtroCategoria }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(productosFiltrados) { producto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(producto.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Categoría: ${producto.categoria}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                            Text(producto.descripcion, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 4.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "$ ${producto.precio}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Button(
                                    onClick = {
                                        val idx = CarritoState.itemsCarrito.indexOfFirst { it.first.id == producto.id }
                                        if (idx >= 0) {
                                            val item = CarritoState.itemsCarrito[idx]
                                            CarritoState.itemsCarrito[idx] = item.first to (item.second + 1)
                                        } else {
                                            CarritoState.itemsCarrito.add(producto to 1)
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Añadir")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}