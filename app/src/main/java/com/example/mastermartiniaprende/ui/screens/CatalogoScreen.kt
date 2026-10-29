package com.example.mastermartiniaprende.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.R
import com.example.mastermartiniaprende.ui.components.MarcaMasterMartini
import com.example.mastermartiniaprende.ui.theme.VerdePistacho
import com.example.mastermartiniaprende.viewmodel.CatalogoViewModel

@Composable
fun CatalogoScreen(
    viewModel: CatalogoViewModel,
    onCursoClick: (Int) -> Unit
) {
    val cursos = viewModel.cursos.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MarcaMasterMartini()

        Text(
            text = "Explora cursos y recetas",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Elige un curso y entra directamente a sus clases paso a paso.",
            style = MaterialTheme.typography.bodyLarge
        )

        cursos.forEach { curso ->
            val cursoCompletado = viewModel.progresoCurso(curso) == 100
            val imagenCurso = when (curso.id) {
                1 -> R.drawable.foto_chocolate_pexels
                2 -> R.drawable.foto_pasteleria_pexels
                3 -> R.drawable.foto_croissants_pexels
                else -> null
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCursoClick(curso.id) },
                shape = RoundedCornerShape(20.dp)
            ) {
                Column {
                    if (imagenCurso != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                        ) {
                            Image(
                                painter = painterResource(id = imagenCurso),
                                contentDescription = "Preparación del curso ${curso.titulo}",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 20.dp,
                                            topEnd = 20.dp
                                        )
                                    ),
                                contentScale = ContentScale.Crop
                            )

                            if (cursoCompletado) {
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(12.dp),
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.surface,
                                    shadowElevation = 4.dp
                                ) {
                                    Text(
                                        text = "✓",
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        ),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = VerdePistacho
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = curso.categoria.uppercase(),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
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
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "Ver curso y recetas →",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
