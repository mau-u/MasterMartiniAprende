package com.example.mastermartiniaprende.ui.screens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.mastermartiniaprende.model.Curso
import com.example.mastermartiniaprende.viewmodel.CatalogoViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun ResultadoFinalScreen(
    curso: Curso,
    viewModel: CatalogoViewModel,
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    var mensaje by remember {
        mutableStateOf("")
    }

    var rutaTemporal by remember {
        mutableStateOf<String?>(null)
    }

    val progreso = viewModel.progresoCurso(curso)
    val rutaFoto = viewModel.obtenerFotoResultado(curso.id)

    val imagen = remember(rutaFoto) {
        rutaFoto?.let { ruta ->
            BitmapFactory.decodeFile(ruta)?.asImageBitmap()
        }
    }

    // Seleccionar una foto desde la galería
    val selectorGaleria =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {
                try {
                    val archivo = File(
                        context.filesDir,
                        "resultado_${curso.id}_${System.currentTimeMillis()}.jpg"
                    )

                    val entrada =
                        context.contentResolver.openInputStream(uri)

                    if (entrada == null) {
                        mensaje = "No se pudo abrir la imagen."
                    } else {
                        entrada.use { input ->
                            FileOutputStream(archivo).use { output ->
                                input.copyTo(output)
                            }
                        }

                        viewModel.guardarFotoResultado(
                            curso.id,
                            archivo.absolutePath
                        )

                        mensaje = "¡Fotografía guardada correctamente!"
                    }
                } catch (e: Exception) {
                    mensaje = "No se pudo guardar la fotografía."
                }
            }
        }

    // Tomar una fotografía con la cámara
    val selectorCamara =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { guardada ->

            if (guardada) {
                val ruta = rutaTemporal

                if (ruta != null) {
                    viewModel.guardarFotoResultado(
                        curso.id,
                        ruta
                    )

                    mensaje = "¡Foto tomada y guardada correctamente!"
                } else {
                    mensaje = "No se encontró la fotografía."
                }
            } else {
                mensaje = "No se tomó ninguna fotografía."
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Mi resultado final",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = curso.titulo,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Progreso del curso: $progreso%",
            style = MaterialTheme.typography.titleMedium
        )

        LinearProgressIndicator(
            progress = { progreso / 100f },
            modifier = Modifier.fillMaxWidth()
        )

        if (progreso == 100) {
            Text(
                text = "¡Felicitaciones! Has completado el curso. " +
                        "Ahora puedes guardar una fotografía de tu preparación."
            )
        } else {
            Text(
                text = "Completa todas las clases para registrar tu resultado final."
            )
        }

        // Mostrar la fotografía guardada
        if (imagen != null) {
            Image(
                bitmap = imagen,
                contentDescription = "Mi resultado final",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Text("Todavía no has guardado una fotografía.")
        }

        // BOTÓN NUEVO: abrir la cámara
        Button(
            onClick = {
                try {
                    val archivo = File(
                        context.filesDir,
                        "resultado_${curso.id}_${System.currentTimeMillis()}.jpg"
                    )

                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        archivo
                    )

                    rutaTemporal = archivo.absolutePath
                    selectorCamara.launch(uri)

                } catch (e: Exception) {
                    mensaje = "No se pudo abrir la cámara."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = progreso == 100
        ) {
            Text("Tomar foto con la cámara")
        }

        // Elegir una fotografía existente
        OutlinedButton(
            onClick = {
                selectorGaleria.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = progreso == 100
        ) {
            Text("Seleccionar foto de la galería")
        }

        if (mensaje.isNotBlank()) {
            Text(
                text = mensaje,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}