package com.example.mastermartiniaprende.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel,
    onLoginExitoso: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        MarcaMasterMartini(grande = true)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.errorEmailLogin.value = null
                viewModel.mensaje.value = ""
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = viewModel.errorEmailLogin.value != null,
            trailingIcon = {
                IconoErrorCampo(visible = viewModel.errorEmailLogin.value != null)
            },
            supportingText = {
                viewModel.errorEmailLogin.value?.let { Text(it) }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.errorPasswordLogin.value = null
                viewModel.mensaje.value = ""
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = viewModel.errorPasswordLogin.value != null,
            trailingIcon = {
                IconoErrorCampo(visible = viewModel.errorPasswordLogin.value != null)
            },
            supportingText = {
                viewModel.errorPasswordLogin.value?.let { Text(it) }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.login(email, password) { usuario ->
                    if (usuario != null) {
                        onLoginExitoso(usuario.email)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.cargando.value
        ) {
            Text(if (viewModel.cargando.value) "Ingresando…" else "Entrar")
        }

        Text(
            text = viewModel.mensaje.value,
            modifier = Modifier.padding(top = 12.dp)
        )

        TextButton(
            onClick = {
                navController.navigate("register") {
                    popUpTo("login") { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al registro")
        }
    }
}
