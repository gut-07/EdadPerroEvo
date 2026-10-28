package com.example.edadperroevo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraScreen() {
    var edadInput by remember { mutableStateOf("") }
    var resultadoEdad by remember { mutableStateOf<Int?>(null) }
    var mensajeHumor by remember { mutableStateOf("") }
    var tamañoSeleccionado by remember { mutableStateOf("Pequeño") }
    var expanded by remember { mutableStateOf(false) }
    val tamaños = listOf("Pequeño", "Mediano", "Grande")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Título comercial llamativo
            Text(
                text = "🐾 The Pet Emporium 🐾",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Calculadora de Edad & Nivel de Sabiduría Canina",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Tarjeta de entrada de datos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Calcula cuántos años humanos tiene tu fiscal",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = edadInput,
                        onValueChange = { edadInput = it },
                        label = { Text("Edad cronológica del perro (años)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = tamañoSeleccionado,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tamaño de la raza") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            tamaños.forEach { tamaño ->
                                DropdownMenuItem(
                                    text = { Text(tamaño) },
                                    onClick = {
                                        tamañoSeleccionado = tamaño
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            val anos = edadInput.toIntOrNull() ?: 0
                            val factor = when (tamañoSeleccionado) {
                                "Pequeño" -> 12
                                "Mediano" -> 14
                                else -> 16
                            }
                            val equivalente = anos * factor
                            resultadoEdad = equivalente

                            // Toque de humor para la presentación
                            mensajeHumor = when {
                                equivalente < 20 -> "🐶 ¡Un tierno cachorro! Destruye tus zapatos favoritos pero lo perdonas al instante."
                                equivalente < 45 -> "🏃 ¡En plena flor de la juventud! Corre tras las motos y cree que es un león."
                                equivalente < 70 -> "☕ ¡Adulto responsable! Ya paga impuestos emocionales y odia las fiestas ruidosas."
                                else -> "🧓 ¡Abuelito legendario! Ya puede cobrar pensión perruna y ronca más fuerte que tú."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Descubrir su Verdadera Edad", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tarjeta de resultados con humor
            if (resultadoEdad != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "¡Resultado Oficial!",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$resultadoEdad años humanos",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = mensajeHumor,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}