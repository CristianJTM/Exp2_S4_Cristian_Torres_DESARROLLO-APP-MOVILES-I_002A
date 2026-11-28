package com.duoc.pizzeriaapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    val mensaje by viewModel.mensaje.observeAsState()
    val productos by viewModel.productos.observeAsState(emptyList())

    var nombre by rememberSaveable { mutableStateOf("") }
    var contacto by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var zona by rememberSaveable { mutableStateOf("") }
    var producto by rememberSaveable { mutableStateOf("") }
    var cantidad by rememberSaveable { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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

            mensaje?.let { texto ->
                Text(
                    text = texto,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Registrar cliente", style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = contacto,
                        onValueChange = { contacto = it },
                        label = { Text("Teléfono (+56 9 5555 1111)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = zona,
                        onValueChange = { zona = it },
                        label = { Text("Zona (Centro, Norte, Sur)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            viewModel.registrarCliente(nombre, contacto, correo, zona)
                            nombre = ""
                            contacto = ""
                            correo = ""
                            zona = ""
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Registrar cliente")
                    }
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Registrar pedido", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = productos.joinToString("\n"),
                        style = MaterialTheme.typography.bodySmall
                    )

                    OutlinedTextField(
                        value = producto,
                        onValueChange = { producto = it },
                        label = { Text("Número de producto") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = cantidad,
                        onValueChange = { cantidad = it },
                        label = { Text("Cantidad (1 a 100)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            viewModel.registrarPedido(producto, cantidad)
                            producto = ""
                            cantidad = ""
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Registrar pedido")
                    }
                }
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
