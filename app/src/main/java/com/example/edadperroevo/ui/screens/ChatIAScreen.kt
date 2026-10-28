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

data class MensajeIA(val texto: String, val esUsuario: Boolean, val esUrgencia: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatIAScreen() {
    var textoConsulta by remember { mutableStateOf("") }
    val mensajes = remember {
        mutableStateListOf(
            MensajeIA("¡Hola! Soy tu asistente de bienestar canino. ¿Qué dudas tienes sobre alimentación, higiene o cuidados?", false),
            MensajeIA("⚠️ Nota: Las recomendaciones de esta IA no sustituyen la valoración de un médico veterinario.", false)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asistente IA - Bienestar Canino") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mensajes) { msg ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (msg.esUsuario) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = when {
                                msg.esUrgencia -> Color(0xFFFFCDD2)
                                msg.esUsuario -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            },
                            modifier = Modifier.widthIn(max = 290.dp)
                        ) {
                            Text(
                                text = msg.texto,
                                modifier = Modifier.padding(12.dp),
                                color = if (msg.esUsuario) Color.White else Color.Black,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = textoConsulta,
                onValueChange = { textoConsulta = it },
                label = { Text("Escribe tu consulta médica o de cuidado...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = {
                    if (textoConsulta.isNotBlank()) {
                        val consulta = textoConsulta
                        mensajes.add(MensajeIA(consulta, true))
                        textoConsulta = ""

                        if (consulta.contains("urgencia", true) || consulta.contains("sangre", true) || consulta.contains("desmayo", true)) {
                            mensajes.add(
                                MensajeIA("🚨 ¡ALERTA VETERINARIA! Los síntomas descritos requieren atención profesional inmediata. Por favor dirígete al módulo de Ubicaciones para contactar la veterinaria 24/7 más cercana.", false, esUrgencia = true)
                            )
                        } else {
                            mensajes.add(
                                MensajeIA("Para '$consulta': Asegúrate de mantener a tu perro hidratado, seguir su esquema de vacunación al día y ofrecer alimento balanceado.", false)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Enviar a Asistente IA")
            }
        }
    }
}