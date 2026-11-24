package com.duoc.pizzeriaapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.pizzeriaapp.ui.theme.PizzeriaAppTheme

// Pantalla de resumen: observa el LiveData del ViewModel y se recompone al cambiar.
@Composable
fun ResumenScreen(viewModel: ResumenViewModel = viewModel()) {
    val totalClientes by viewModel.totalClientes.observeAsState(0)
    val totalPedidos by viewModel.totalPedidos.observeAsState(0)
    val nombreUltimoCliente by viewModel.nombreUltimoCliente.observeAsState("Sin clientes")

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Greeting(name = "Android")

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Resumen", style = MaterialTheme.typography.titleLarge)
                    Text("Clientes registrados: $totalClientes", style = MaterialTheme.typography.bodyLarge)
                    Text("Pedidos realizados: $totalPedidos", style = MaterialTheme.typography.bodyLarge)
                    Text("Último cliente: $nombreUltimoCliente", style = MaterialTheme.typography.bodyLarge)
                }
            }

            // Acción de demo: registra un cliente y un pedido para ver el resumen actualizarse.
            Button(
                onClick = { viewModel.registrarClienteYPedidoDemo() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar cliente y pedido de prueba")
            }
        }
    }
}

// Saludo de bienvenida de la semana 4 (texto exacto).
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Bienvenidos a la mejor pizzería de la ciudad",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ResumenScreenPreview() {
    PizzeriaAppTheme {
        ResumenScreen()
    }
}
