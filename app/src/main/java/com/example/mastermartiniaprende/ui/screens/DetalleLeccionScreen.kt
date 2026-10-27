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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.model.Leccion

@Composable
fun DetalleLeccionScreen(
    leccion: Leccion,
    onVolver: () -> Unit,
    onCompletar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Clase ${leccion.numero}",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = leccion.titulo,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "${leccion.duracionMinutos} minutos",
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = leccion.descripcion,
            style = MaterialTheme.typography.bodyLarge
        )

        // Materiales necesarios
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Materiales e ingredientes",
                    style = MaterialTheme.typography.titleMedium
                )

                leccion.materiales.forEach { material ->
                    Text(text = "• $material")
                }
            }
        }

        // Instrucciones de la lección
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Paso a paso",
                    style = MaterialTheme.typography.titleMedium
                )

                leccion.pasos.forEachIndexed { indice, paso ->
                    Text(
                        text = "${indice + 1}. $paso",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onCompletar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Marcar clase como completada")
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver a las clases")
        }
    }
}