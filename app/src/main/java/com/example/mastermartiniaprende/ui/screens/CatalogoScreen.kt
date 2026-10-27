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

        Text(
            text = "Master Martini Aprende",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Aprende a tu manera",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Explora nuestros cursos de gastronomía."
        )

        cursos.forEach { curso ->

            val imagenCurso = when (curso.id) {
                1 -> R.drawable.chocolate_brillante
                2 -> R.drawable.pasteleria_vitrina
                3 -> R.drawable.masas_bolleria
                else -> null
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onCursoClick(curso.id)
                    },
                shape = RoundedCornerShape(16.dp)
            ) {
                Column {

                    if (imagenCurso != null) {
                        Image(
                            painter = painterResource(
                                id = imagenCurso
                            ),
                            contentDescription =
                                "Resultado esperado de ${curso.titulo}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp
                                    )
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = curso.categoria.uppercase(),
                            style =
                                MaterialTheme.typography.labelMedium,
                            color =
                                MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = curso.titulo,
                            style =
                                MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = curso.descripcion,
                            style =
                                MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "${curso.cantidadClases} clases · Nivel ${curso.nivel}",
                            style =
                                MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}