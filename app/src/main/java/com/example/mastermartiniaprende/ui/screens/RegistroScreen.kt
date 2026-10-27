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
import com.example.mastermartiniaprende.ui.components.IconoErrorCampo
import com.example.mastermartiniaprende.ui.components.MarcaMasterMartini
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
        MarcaMasterMartini(grande = true)
        Text(
            text = "Crea tu cuenta",
            style = MaterialTheme.typography.titleLarge
        )

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
            trailingIcon = {
                IconoErrorCampo(visible = viewModel.errorNombreRegistro.value != null)
            },
            supportingText = {
                viewModel.errorNombreRegistro.value?.let { Text(it) }
            }
        )

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
            trailingIcon = {
                IconoErrorCampo(visible = viewModel.errorEmailRegistro.value != null)
            },
            supportingText = {
                viewModel.errorEmailRegistro.value?.let { Text(it) }
            }
        )

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
            trailingIcon = {
                IconoErrorCampo(visible = viewModel.errorTipoNegocioRegistro.value != null)
            },
            supportingText = {
                val error = viewModel.errorTipoNegocioRegistro.value
                Text(error ?: "Ej.: pastelería, panadería o cafetería")
            }
        )

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
            trailingIcon = {
                IconoErrorCampo(visible = viewModel.errorPasswordRegistro.value != null)
            },
            supportingText = {
                viewModel.errorPasswordRegistro.value?.let { Text(it) }
            }
        )

        Button(
            onClick = {
                viewModel.registrar(
                    nombre = nombre,
                    email = email,
                    password = password,
                    tipoNegocio = tipoNegocio
                ) { registroExitoso ->
                    if (registroExitoso) {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.cargando.value
        ) {
            Text(if (viewModel.cargando.value) "Guardando cuenta…" else "Crear cuenta")
        }

        Text(
            text = viewModel.mensaje.value,
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            onClick = {
                navController.navigate("login") {
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}
