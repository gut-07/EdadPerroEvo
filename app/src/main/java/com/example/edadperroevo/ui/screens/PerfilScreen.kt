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

data class Perro(val nombre: String, val raza: String, val edad: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen() {
    var nombreUsuario by remember { mutableStateOf("Sebastián Gutiérrez") }
    var correoUsuario by remember { mutableStateOf("sebastian@usb.edu.co") }

    var nombrePerro by remember { mutableStateOf("") }
    var razaPerro by remember { mutableStateOf("") }
    var edadPerro by remember { mutableStateOf("") }

    val listaPerros = remember {
        mutableStateListOf(
            Perro("Firulais", "Golden Retriever", "3 años"),
            Perro("Rocky", "Beagle", "5 años")
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil de Usuario & Mascotas") }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Sección 1: Datos del Usuario
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Datos del Usuario (Autenticación)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = nombreUsuario,
                            onValueChange = { nombreUsuario = it },
                            label = { Text("Nombre Completo") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = correoUsuario,
                            onValueChange = { correoUsuario = it },
                            label = { Text("Correo Electrónico") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Sección 2: Registro de Mascotas
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Registrar Nueva Mascota", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = nombrePerro,
                            onValueChange = { nombrePerro = it },
                            label = { Text("Nombre del Perro") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = razaPerro,
                            onValueChange = { razaPerro = it },
                            label = { Text("Raza") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = edadPerro,
                            onValueChange = { edadPerro = it },
                            label = { Text("Edad (ej. 2 años)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                if (nombrePerro.isNotBlank() && razaPerro.isNotBlank()) {
                                    listaPerros.add(Perro(nombrePerro, razaPerro, edadPerro))
                                    nombrePerro = ""
                                    razaPerro = ""
                                    edadPerro = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Añadir Perro al Perfil", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Sección 3: Título de la lista
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Mis Perros Registrados (${listaPerros.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Sección 4: Renderizado explícito de los elementos con altura asegurada
            items(listaPerros) { perro ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp), // Altura fija garantizada para evitar colapsos visuales
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(perro.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("Raza: ${perro.raza}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                        }
                        AssistChip(
                            onClick = {},
                            label = { Text(perro.edad, fontWeight = FontWeight.SemiBold) }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}