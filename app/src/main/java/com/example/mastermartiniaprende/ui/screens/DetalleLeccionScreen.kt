package com.example.mastermartiniaprende.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.R
import com.example.mastermartiniaprende.model.Leccion
import com.example.mastermartiniaprende.ui.components.ReproductorVideoLocal

@Composable
fun DetalleLeccionScreen(
    leccion: Leccion,
    onVolver: () -> Unit,
    onCompletar: () -> Unit
) {
    val imagen = when (leccion.cursoId) {
        1 -> when (leccion.numero) {
            1 -> R.drawable.ingredientes_chocolate
            2 -> R.drawable.base_chocolate
            3 -> R.drawable.ganache_chocolate
            4 -> R.drawable.cobertura_chocolate
            5 -> R.drawable.resultado_chocolate
            else -> null
        }
        2 -> R.drawable.foto_pasteleria_pexels
        3 -> R.drawable.foto_croissants_pexels
        else -> null
    }

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

        if (imagen != null) {
            Image(
                painter = painterResource(imagen),
                contentDescription = "Fotografía para la clase: ${leccion.titulo}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Text(leccion.titulo, style = MaterialTheme.typography.titleLarge)
        Text("${leccion.duracionMinutos} minutos", style = MaterialTheme.typography.labelLarge)
        Text(leccion.descripcion, style = MaterialTheme.typography.bodyLarge)

        leccion.videoResourceId?.let { videoId ->
            Text(
                text = "Video de apoyo",
                style = MaterialTheme.typography.titleMedium
            )
            ReproductorVideoLocal(
                videoResourceId = videoId,
                imagenPortada = imagen ?: R.drawable.foto_chocolate_pexels,
                titulo = "Demostración: ${leccion.titulo}"
            )
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Materiales e ingredientes",
                    style = MaterialTheme.typography.titleMedium
                )
                leccion.materiales.forEach { material ->
                    Text(text = "• $material", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Receta paso a paso", style = MaterialTheme.typography.titleMedium)
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
