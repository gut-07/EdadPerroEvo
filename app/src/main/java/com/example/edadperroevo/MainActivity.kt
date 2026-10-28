package com.example.edadperroevo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.edadperroevo.ui.screens.MainScreen
import com.example.edadperroevo.ui.theme.EdadPerroEvoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdadPerroEvoTheme {
                MainScreen()
            }
        }
    }
}