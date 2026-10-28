package com.example.edadperroevo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Veterinaria(
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val distancia: String,
    val esUrgencias24h: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    var busquedaCiudad by remember { mutableStateOf("Bogotá") }
    val directorioveterinarias = listOf(
        Veterinaria("Clínica Veterinaria San Martín", "Av. Caracas # 45-12", "601 555 0192", "0.8 km", true),
        Veterinaria("Centro Veterinario El Chicó", "Carrera 15 # 88-30", "601 312 4455", "1.5 km", true),
        Veterinaria("Hospital Canino Central", "Calle 100 # 19-45", "601 620 9988", "2.3 km", false),
        Veterinaria("Spa & Salud Canina", "Calle 140 # 11-15", "601 702 1122", "3.1 km", false)
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Veterinarias & Urgencias Cercanas") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = busquedaCiudad,
                onValueChange = { busquedaCiudad = it },
                label = { Text("Buscar por ciudad o dirección") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    text = "📍 Ubicación detectada en $busquedaCiudad (GPS activo)",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(directorioveterinarias) { vet ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(vet.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                if (vet.esUrgencias24h) {
                                    Badge(containerColor = Color(0xFFD32F2F)) {
                                        Text("URGENCIAS 24/7", color = Color.White, modifier = Modifier.padding(2.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("📍 Direcc: ${vet.direccion}", style = MaterialTheme.typography.bodyMedium)
                            Text("📞 Tel: ${vet.telefono} | Distancia: ${vet.distancia}", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = { },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Abrir indicaciones de ruta")
                            }
                        }
                    }
                }
            }
        }
    }
}