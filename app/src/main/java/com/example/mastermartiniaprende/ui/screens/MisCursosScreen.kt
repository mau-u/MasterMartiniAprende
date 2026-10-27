package com.example.mastermartiniaprende.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.viewmodel.CatalogoViewModel

@Composable
fun MisCursosScreen(
    viewModel: CatalogoViewModel,
    onCursoClick: (Int) -> Unit,
    onExplorarCursos: () -> Unit
) {
    val misCursos = viewModel.obtenerMisCursos()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mis cursos",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Continúa aprendiendo a tu ritmo.",
            style = MaterialTheme.typography.bodyLarge
        )

        if (misCursos.isEmpty()) {
            Text(
                text = "Todavía no te has inscrito en ningún curso."
            )

            Button(
                onClick = onExplorarCursos,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Explorar cursos")
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = misCursos,
                    key = { curso -> curso.id }
                ) { curso ->

                    val progreso = viewModel.progresoCurso(curso)
                    val progresoAnimado by animateFloatAsState(
                        targetValue = progreso.coerceIn(0, 100) / 100f,
                        label = "avanceCurso${curso.id}"
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = curso.categoria.uppercase(),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = curso.titulo,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = curso.descripcion,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "${curso.cantidadClases} clases · " +
                                        "Nivel ${curso.nivel}"
                            )

                            Text(
                                text = "Progreso: $progreso%"
                            )

                            LinearProgressIndicator(
                                progress = { progresoAnimado },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Button(
                                onClick = {
                                    onCursoClick(curso.id)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Continuar curso")
                            }
                        }
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onExplorarCursos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Explorar más cursos")
        }
    }
}
