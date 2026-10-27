package com.example.mastermartiniaprende.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.ui.components.MarcaMasterMartini
import com.example.mastermartiniaprende.viewmodel.AuthViewModel

@Composable
fun PerfilScreen(
    viewModel: AuthViewModel,
    onCerrarSesion: () -> Unit
) {
    val usuario = viewModel.perfilActual.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        MarcaMasterMartini()
        Text(
            text = "Mi perfil",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val inicial = usuario?.nombre?.firstOrNull()?.uppercaseChar()?.toString() ?: "M"
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = inicial,
                        modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Text(
                    text = usuario?.nombre ?: "Usuario",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = usuario?.email ?: "No hay una sesión activa",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("Mi negocio", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = usuario?.tipoNegocio?.takeIf { it.isNotBlank() }
                        ?: "Tipo de negocio no informado",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Text(
            text = "Tus cursos y avances se guardan en este dispositivo.",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onCerrarSesion,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Cerrar sesión")
        }
    }
}

