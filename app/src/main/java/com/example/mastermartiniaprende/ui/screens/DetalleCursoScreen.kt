package com.example.mastermartiniaprende.ui.screens

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
import com.example.mastermartiniaprende.ui.components.IndicadorProgresoCurso
import com.example.mastermartiniaprende.ui.components.ReproductorVideoLocal
import com.example.mastermartiniaprende.viewmodel.CatalogoViewModel
import com.example.mastermartiniaprende.viewmodel.LeccionViewModel

@Composable
fun DetalleCursoScreen(
    curso: Curso,
    leccionViewModel: LeccionViewModel,
    catalogoViewModel: CatalogoViewModel,
    onLeccionClick: (Int) -> Unit,
    onVolver: () -> Unit,
    onComenzar: () -> Unit
) {
    LaunchedEffect(curso.id) {
        leccionViewModel.cargarLecciones(curso.id)
    }
    val lecciones = leccionViewModel.lecciones.value
    val leccionConVideo = lecciones.firstOrNull { it.videoResourceId != null }

    val imagenCurso = when (curso.id) {
        1 -> R.drawable.foto_chocolate_pexels
        2 -> R.drawable.foto_pasteleria_pexels
        3 -> R.drawable.foto_croissants_pexels
        else -> R.drawable.foto_chocolate_pexels
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Detalle del curso",
            style = MaterialTheme.typography.headlineSmall
        )

        androidx.compose.foundation.Image(
            painter = painterResource(id = imagenCurso),
            contentDescription = "Preparación del curso ${curso.titulo}",
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = curso.categoria.uppercase(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(curso.titulo, style = MaterialTheme.typography.titleLarge)
                Text(curso.descripcion, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "${curso.cantidadClases} clases · Nivel ${curso.nivel}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        IndicadorProgresoCurso(
            curso = curso,
            clasesCompletadas = catalogoViewModel.cantidadClasesCompletadas(curso)
        )

        leccionConVideo?.let { leccionVideo ->
            Text(
                text = "Video incluido en este curso",
                style = MaterialTheme.typography.titleLarge
            )
            ReproductorVideoLocal(
                videoResourceId = leccionVideo.videoResourceId!!,
                imagenPortada = imagenCurso,
                titulo = "Demostración: ${leccionVideo.titulo}"
            )
        }
        Text(
            text = "Clases y recetas",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Abre una clase para ver sus ingredientes y la receta paso a paso.",
            style = MaterialTheme.typography.bodyLarge
        )

        lecciones.forEach { leccion ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLeccionClick(leccion.id) },
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "RECETA ${leccion.numero}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(leccion.titulo, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "${leccion.duracionMinutos} min · Ver ingredientes y preparación",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Button(
            onClick = onComenzar,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Ver todas las clases y recetas")
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Volver al catálogo")
        }
    }
}
