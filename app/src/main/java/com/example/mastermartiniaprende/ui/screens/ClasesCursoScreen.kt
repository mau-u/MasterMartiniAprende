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
        Text("Clases y recetas", style = MaterialTheme.typography.headlineSmall)
        Text(curso.titulo, style = MaterialTheme.typography.titleLarge)
        IndicadorProgresoCurso(
            curso = curso,
            clasesCompletadas = viewModel.cantidadClasesCompletadas(curso)
        )
        Text("Abre una clase para ver ingredientes, fotos y receta paso a paso.", style = MaterialTheme.typography.bodyLarge)

        if (lecciones.isEmpty()) {
            Text("Todavía no hay lecciones disponibles para este curso.")
        } else {
            lecciones.forEach { leccion ->
                val completada = viewModel.estaClaseCompletada(curso.id, leccion.numero)
                val imagenLeccion = when (curso.id) {
                    1 -> when (leccion.numero) {
                        1 -> R.drawable.ingredientes_chocolate
                        2 -> R.drawable.base_chocolate
                        3 -> R.drawable.ganache_chocolate
                        4 -> R.drawable.cobertura_chocolate
                        5 -> R.drawable.resultado_chocolate
                        else -> null
                    }
                    2 -> R.drawable.pasteleria_vitrina
                    3 -> R.drawable.masas_bolleria
                    else -> null
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLeccionClick(leccion.id) }
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (imagenLeccion != null) {
                            Image(
                                painter = painterResource(imagenLeccion),
                                contentDescription = "Imagen de ${leccion.titulo}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text("Clase ${leccion.numero}", style = MaterialTheme.typography.labelLarge)
                        Text(leccion.titulo, style = MaterialTheme.typography.titleMedium)
                        Text(leccion.descripcion, style = MaterialTheme.typography.bodyMedium)
                        Text("${leccion.duracionMinutos} minutos")
                        Text(
                            text = if (completada) "✓ Completada" else "Pendiente",
                            color = if (completada) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "Tocar para ver la receta y sus ingredientes",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

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
