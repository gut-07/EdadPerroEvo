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

data class MensajeComunidad(
    val id: Int,
    val remitente: String,
    val texto: String,
    val timestamp: String,
    val esMio: Boolean
)

object ChatComunidadState {
    val mensajes = mutableStateListOf(
        MensajeComunidad(1, "Carlos Mendoza", "¿Alguien sabe a qué hora abre el parque canino del norte?", "10:15 AM", false),
        MensajeComunidad(2, "María Pérez", "Hola Carlos, abre desde las 6:00 AM y es excelente para correr con ellos.", "10:18 AM", false)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    var textoMensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comunidad Canina (Chat P2P)") },
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
                items(ChatComunidadState.mensajes) { msg ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (msg.esMio) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (msg.esMio) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = msg.remitente,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (msg.esMio) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = msg.texto,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = msg.timestamp,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                value = textoMensaje,
                onValueChange = { textoMensaje = it },
                label = { Text("Escribe un mensaje a la comunidad...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = {
                    if (textoMensaje.isNotBlank()) {
                        val nuevoId = ChatComunidadState.mensajes.size + 1
                        ChatComunidadState.mensajes.add(
                            MensajeComunidad(
                                id = nuevoId,
                                remitente = "Tú (Sebastián)",
                                texto = textoMensaje,
                                timestamp = "Justo ahora",
                                esMio = true
                            )
                        )
                        textoMensaje = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Enviar Mensaje a la Comunidad", fontWeight = FontWeight.Bold)
            }
        }
    }
}