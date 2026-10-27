package com.example.mastermartiniaprende.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.mastermartiniaprende.viewmodel.CatalogoViewModel

@Composable
fun MisLogrosScreen(
    viewModel: CatalogoViewModel,
    onVolver: () -> Unit
) {
    val cursosConFoto = viewModel.obtenerCursosConFoto()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mis logros 🏆",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Tus preparaciones y trofeos conseguidos.",
            style = MaterialTheme.typography.bodyLarge
        )

        if (cursosConFoto.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "🏆",
                        style = MaterialTheme.typography.displayMedium
                    )

                    Text(
                        text = "¡Tu colección comienza aquí!",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Completa un curso y guarda una foto de tu " +
                                "preparación para ver tu primer logro."
                    )
                }
            }
        } else {
            cursosConFoto.forEach { curso ->

                val rutaFoto = viewModel.obtenerFotoResultado(curso.id)

                val imagen = remember(rutaFoto) {
                    rutaFoto?.let {
                        BitmapFactory.decodeFile(it)?.asImageBitmap()
                    }
                }

                AnimatedVisibility(
                    visible = imagen != null,
                    enter = fadeIn(tween(350)) +
                            scaleIn(
                                initialScale = 0.85f,
                                animationSpec = tween(350)
                            )
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "🏆 Trofeo desbloqueado",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = when (curso.id) {
                                    1 -> "Maestro del chocolate"
                                    2 -> "Maestro pastelero"
                                    3 -> "Maestro de masas"
                                    else -> curso.titulo
                                },
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = curso.titulo,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            if (imagen != null) {
                                Image(
                                    bitmap = imagen,
                                    contentDescription =
                                        "Mi preparación: ${curso.titulo}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(230.dp)
                                        .clip(RoundedCornerShape(14.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Text(
                                text = "Fotografía de tu preparación guardada."
                            )
                        }
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}