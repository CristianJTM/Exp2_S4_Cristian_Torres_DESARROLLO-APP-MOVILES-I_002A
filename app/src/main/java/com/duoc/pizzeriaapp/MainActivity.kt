package com.duoc.pizzeriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.duoc.pizzeriaapp.ui.ResumenScreen
import com.duoc.pizzeriaapp.ui.theme.PizzeriaAppTheme

// Host mínimo: aplica el tema y monta la pantalla del módulo :ui.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzeriaAppTheme {
                ResumenScreen()
            }
        }
    }
}
