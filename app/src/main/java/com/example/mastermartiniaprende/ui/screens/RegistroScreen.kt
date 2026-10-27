package com.example.mastermartiniaprende.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mastermartiniaprende.viewmodel.AuthViewModel

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tipoNegocio by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Master Martini Aula",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Crea tu cuenta",
            style = MaterialTheme.typography.titleMedium
        )

        // Campo: Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                viewModel.errorNombreRegistro.value = null
            },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = viewModel.errorNombreRegistro.value != null,
            supportingText = {
                viewModel.errorNombreRegistro.value?.let { error ->
                    Text(error)
                }
            }
        )

        // Campo: Correo
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.errorEmailRegistro.value = null
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = viewModel.errorEmailRegistro.value != null,
            supportingText = {
                viewModel.errorEmailRegistro.value?.let { error ->
                    Text(error)
                }
            }
        )

        // Campo: Tipo de negocio
        OutlinedTextField(
            value = tipoNegocio,
            onValueChange = {
                tipoNegocio = it
                viewModel.errorTipoNegocioRegistro.value = null
            },
            label = { Text("Tipo de negocio o emprendimiento") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = viewModel.errorTipoNegocioRegistro.value != null,
            supportingText = {
                val error = viewModel.errorTipoNegocioRegistro.value

                Text(
                    error ?: "Ej.: pastelería, panadería o cafetería"
                )
            }
        )

        // Campo: Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.errorPasswordRegistro.value = null
            },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = viewModel.errorPasswordRegistro.value != null,
            supportingText = {
                viewModel.errorPasswordRegistro.value?.let { error ->
                    Text(error)
                }
            }
        )

        // Botón para registrar
        Button(
            onClick = {
                val registroExitoso = viewModel.registrar(
                    nombre = nombre,
                    email = email,
                    password = password,
                    tipoNegocio = tipoNegocio
                )

                if (registroExitoso) {
                    navController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear cuenta")
        }

        // Mensaje de resultado
        Text(
            text = viewModel.mensaje.value,
            modifier = Modifier.fillMaxWidth()
        )

        // Navegar al login
        TextButton(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}