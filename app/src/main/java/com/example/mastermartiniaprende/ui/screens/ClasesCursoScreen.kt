package com.example.mastermartiniaprende.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.R
import com.example.mastermartiniaprende.model.Curso
import com.example.mastermartiniaprende.viewmodel.CatalogoViewModel
import com.example.mastermartiniaprende.viewmodel.LeccionViewModel

@Composable
fun ClasesCursoScreen(
    curso: Curso,
    viewModel: CatalogoViewModel,
    leccionViewModel: LeccionViewModel,
    onLeccionClick: (Int) -> Unit,
    onResultadoClick: () -> Unit,
    onVolver: () -> Unit
) {
    // Cargar las lecciones del curso seleccionado
    LaunchedEffect(curso.id) {
        leccionViewModel.cargarLecciones(curso.id)
    }

    val lecciones = leccionViewModel.lecciones.value
    val progreso = viewModel.progresoCurso(curso)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Clases del curso",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = curso.titulo,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Tu progreso: $progreso%",
            style = MaterialTheme.typography.titleMedium
        )

        LinearProgressIndicator(
            progress = { progreso / 100f },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Selecciona una clase para consultar su contenido."
        )

        if (lecciones.isEmpty()) {
            Text(
                text = "Todavía no hay lecciones disponibles para este curso."
            )
        } else {
            lecciones.forEach { leccion ->

                val completada = viewModel.estaClaseCompletada(
                    cursoId = curso.id,
                    numeroClase = leccion.numero
                )

                // Seleccionar la foto según la clase
                val imagenLeccion = if (curso.id == 1) {
                    when (leccion.numero) {
                        1 -> R.drawable.ingredientes_chocolate
                        2 -> R.drawable.base_chocolate
                        3 -> R.drawable.ganache_chocolate
                        4 -> R.drawable.cobertura_chocolate
                        5 -> R.drawable.resultado_chocolate
                        else -> null
                    }
                } else {
                    null
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onLeccionClick(leccion.id)
                            }
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Mostrar la fotografía de la clase
                        if (imagenLeccion != null) {
                            Image(
                                painter = painterResource(
                                    id = imagenLeccion
                                ),
                                contentDescription =
                                    "Imagen de ${leccion.titulo}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(
                                        RoundedCornerShape(12.dp)
                                    ),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Text(
                            text = "Clase ${leccion.numero}",
                            style = MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = leccion.titulo,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = leccion.descripcion,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "${leccion.duracionMinutos} minutos"
                        )

                        Text(
                            text = if (completada) {
                                "✓ Completada"
                            } else {
                                "Pendiente"
                            },
                            color = if (completada) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )

                        Text(
                            text = "Tocar para ver la lección",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Registrar el resultado cuando el progreso llegue al 100 %
        if (progreso == 100) {
            Button(
                onClick = onResultadoClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar mi resultado final")
            }
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al curso")
        }
    }
}