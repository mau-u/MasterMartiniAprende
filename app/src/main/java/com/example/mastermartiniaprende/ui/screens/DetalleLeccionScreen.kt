package com.example.mastermartiniaprende.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.R
import com.example.mastermartiniaprende.model.Leccion
import com.example.mastermartiniaprende.ui.components.ReproductorVideoLocal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetalleLeccionScreen(
    leccion: Leccion,
    onVolver: () -> Unit,
    onCompletar: () -> Unit
) {
    var completando by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val imagen: Int? = leccion.imagenResourceId

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

        AnimatedVisibility(
            visible = completando,
            enter = fadeIn(tween(250)) +
                    slideInVertically(
                        initialOffsetY = { it / 3 },
                        animationSpec = tween(250)
                    )
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "✓ ¡Clase completada! Muy buen trabajo.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

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
                    Text(
                        text = "• $material",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Receta paso a paso",
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
            onClick = {
                if (!completando) {
                    completando = true

                    scope.launch {
                        delay(650)
                        onCompletar()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !completando
        ) {
            Text(
                if (completando) {
                    "¡Clase completada!"
                } else {
                    "Marcar clase como completada"
                }
            )
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth(),
            enabled = !completando
        ) {
            Text("Volver a las clases")
        }
    }
}
