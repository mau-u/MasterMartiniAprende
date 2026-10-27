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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.R
import com.example.mastermartiniaprende.ui.components.MarcaMasterMartini
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
                        Image(
                            painter = painterResource(id = imagenCurso),
                            contentDescription = "Preparación del curso ${curso.titulo}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 20.dp,
                                        topEnd = 20.dp
                                    )
                                ),
                            contentScale = ContentScale.Crop
                        )
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
