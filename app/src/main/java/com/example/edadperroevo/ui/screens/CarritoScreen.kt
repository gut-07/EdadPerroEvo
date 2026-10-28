package com.example.edadperroevo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(onNavigateToMarketplace: () -> Unit) {
    val subtotal = CarritoState.itemsCarrito.sumOf { it.first.precio * it.second }
    val costoEnvio = if (subtotal > 0.0 && subtotal < 100000.0) 10000.0 else 0.0
    val total = subtotal + costoEnvio

    var direccionEnvio by remember { mutableStateOf("Calle 100 # 15-20, Bogotá") }
    var estadoPago by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Carrito y Pasarela de Pagos") }) }
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Resumen de la Compra", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                // Botón directo para regresar al Marketplace
                TextButton(onClick = onNavigateToMarketplace) {
                    Text("← Volver a la Tienda")
                }
            }

            if (CarritoState.itemsCarrito.isEmpty() && estadoPago.isEmpty()) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("El carrito está vacío.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onNavigateToMarketplace) {
                            Text("Ir a comprar alimentos")
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(CarritoState.itemsCarrito) { (prod, cant) ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(prod.nombre, fontWeight = FontWeight.Bold)
                                    Text("$ ${prod.precio} c/u", style = MaterialTheme.typography.bodySmall)
                                }

                                // Controles de cantidad (+ / - / eliminar)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = {
                                        val idx = CarritoState.itemsCarrito.indexOfFirst { it.first.id == prod.id }
                                        if (idx >= 0) {
                                            val current = CarritoState.itemsCarrito[idx]
                                            if (current.second > 1) {
                                                CarritoState.itemsCarrito[idx] = current.first to (current.second - 1)
                                            } else {
                                                CarritoState.itemsCarrito.removeAt(idx)
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Default.Remove, contentDescription = "Disminuir", modifier = Modifier.size(18.dp))
                                    }

                                    Text("$cant", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp))

                                    IconButton(onClick = {
                                        val idx = CarritoState.itemsCarrito.indexOfFirst { it.first.id == prod.id }
                                        if (idx >= 0) {
                                            val current = CarritoState.itemsCarrito[idx]
                                            CarritoState.itemsCarrito[idx] = current.first to (current.second + 1)
                                        }
                                    }) {
                                        Icon(Icons.Default.Add, contentDescription = "Aumentar", modifier = Modifier.size(18.dp))
                                    }

                                    IconButton(onClick = {
                                        CarritoState.itemsCarrito.removeAll { it.first.id == prod.id }
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                value = direccionEnvio,
                onValueChange = { direccionEnvio = it },
                label = { Text("Dirección de Entrega") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Subtotal:", style = MaterialTheme.typography.bodyMedium)
                        Text("$ $subtotal", style = MaterialTheme.typography.bodyMedium)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Costo de envío:", style = MaterialTheme.typography.bodyMedium)
                        Text(if (costoEnvio > 0.0) "$ $costoEnvio" else "¡GRATIS!", style = MaterialTheme.typography.bodyMedium)
                    }
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total a Pagar:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text("$ $total", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            Button(
                onClick = {
                    if (CarritoState.itemsCarrito.isNotEmpty()) {
                        estadoPago = "✅ ¡Pago APROBADO! Transacción procesada con éxito (Sandbox Wompi / PayU)."
                        CarritoState.itemsCarrito.clear()
                    } else {
                        estadoPago = "El carrito está vacío. Agrega productos."
                    }
                },
                modifier = Modifier.fillMaxWidth().height(46.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Pagar Ahora (Simulación Pasarela)", fontWeight = FontWeight.Bold)
            }

            if (estadoPago.isNotEmpty()) {
                Text(
                    estadoPago,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}