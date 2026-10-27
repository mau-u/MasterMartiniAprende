package com.example.mastermartiniaprende.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.mastermartiniaprende.model.Curso

@Composable
fun DetalleCursoScreen(
    curso: Curso,
    onVolver: () -> Unit,
    onComenzar: () -> Unit
) {

    // Seleccionar la fotografía correspondiente al curso
    val imagenCurso = when (curso.id) {
        1 -> R.drawable.chocolate_brillante
        2 -> R.drawable.pasteleria_vitrina
        3 -> R.drawable.masas_bolleria
        else -> R.drawable.chocolate_brillante
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Detalle del curso",
            style = MaterialTheme.typography.headlineSmall
        )

        // Información del curso
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = curso.categoria.uppercase(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = curso.titulo,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = curso.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "${curso.cantidadClases} clases · Nivel ${curso.nivel}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Fotografía del resultado esperado
        Image(
            painter = painterResource(id = imagenCurso),
            contentDescription = "Resultado esperado de ${curso.titulo}",
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "¿Qué aprenderás a preparar?",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Explora las clases y sigue el paso a paso para conseguir tu propia preparación."
        )

        // Comenzar el curso
        Button(
            onClick = onComenzar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Comenzar curso")
        }

        // Volver al catálogo
        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al catálogo")
        }
    }
}