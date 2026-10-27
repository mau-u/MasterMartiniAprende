package com.example.mastermartiniaprende.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.core.content.ContextCompat
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

    var fotoPendiente by remember {
        mutableStateOf<String?>(null)
    }

    val progreso = viewModel.progresoCurso(curso)
    val rutaGuardada = viewModel.obtenerFotoResultado(curso.id)

    val imagenGuardada = remember(rutaGuardada) {
        rutaGuardada?.let {
            BitmapFactory.decodeFile(it)?.asImageBitmap()
        }
    }

    val imagenPendiente = remember(fotoPendiente) {
        fotoPendiente?.let {
            BitmapFactory.decodeFile(it)?.asImageBitmap()
        }
    }

    val nombreTrofeo = when (curso.id) {
        1 -> "Maestro del chocolate"
        2 -> "Maestro pastelero"
        3 -> "Maestro de masas"
        else -> "Curso completado"
    }

    val selectorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            try {
                val archivo = File(
                    context.filesDir,
                    "resultado_${curso.id}_${System.currentTimeMillis()}.jpg"
                )

                val entrada = context.contentResolver.openInputStream(uri)
                    ?: error("No se pudo abrir la imagen.")

                entrada.use { input ->
                    FileOutputStream(archivo).use { output ->
                        input.copyTo(output)
                    }
                }

                fotoPendiente = archivo.absolutePath
                mensaje = "Revisa tu fotografía y guárdala en este curso."
            } catch (error: Exception) {
                mensaje = "No se pudo preparar la fotografía."
            }
        }
    }

    val selectorCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { guardada ->
        if (guardada) {
            val ruta = rutaTemporal

            if (ruta != null && File(ruta).length() > 0L) {
                fotoPendiente = ruta
                mensaje = "Revisa tu fotografía y guárdala en este curso."
            } else {
                mensaje = "No se encontró la fotografía tomada."
            }
        } else {
            rutaTemporal?.let { File(it).delete() }
            mensaje = "No se tomó ninguna fotografía."
        }
    }

    val abrirCamara: () -> Unit = {
        try {
            val archivo = File(
                context.filesDir,
                "resultado_${curso.id}_${System.currentTimeMillis()}.jpg"
            )

            archivo.createNewFile()

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                archivo
            )

            rutaTemporal = archivo.absolutePath
            selectorCamara.launch(uri)
        } catch (error: Exception) {
            mensaje = "No se pudo abrir la cámara."
        }
    }

    val permisoCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            abrirCamara()
        } else {
            mensaje = "Para tomar la foto, permite el acceso a la cámara."
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
                text = "¡Felicitaciones! Completaste el curso. " +
                        "Ahora puedes guardar una foto de tu preparación."
            )
        } else {
            Text(
                text = "Completa todas las clases para registrar tu resultado final."
            )
        }

        if (imagenPendiente != null) {
            Text(
                text = "Vista previa de tu foto",
                style = MaterialTheme.typography.titleMedium
            )

            Image(
                bitmap = imagenPendiente,
                contentDescription = "Vista previa de mi resultado",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            Button(
                onClick = {
                    val ruta = fotoPendiente

                    if (ruta != null) {
                        viewModel.guardarFotoResultado(
                            curso.id,
                            ruta
                        ) { guardada ->
                            if (guardada) {
                                fotoPendiente = null
                                mensaje = "¡Foto guardada en el curso!"
                            } else {
                                mensaje = viewModel.mensaje.value
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar foto en mi curso")
            }
        } else if (imagenGuardada != null) {
            Text(
                text = "Foto guardada en este curso",
                style = MaterialTheme.typography.titleMedium
            )

            Image(
                bitmap = imagenGuardada,
                contentDescription = "Mi resultado final guardado",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Text("Todavía no has guardado una fotografía.")
        }

        // Trofeo animado: curso completo y fotografía guardada
        AnimatedVisibility(
            visible = progreso == 100 &&
                    rutaGuardada != null &&
                    imagenGuardada != null,
            enter = fadeIn(tween(350)) +
                    scaleIn(
                        initialScale = 0.75f,
                        animationSpec = tween(350)
                    )
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
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
                        text = "¡Trofeo desbloqueado!",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = nombreTrofeo,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Has completado el curso y guardado tu preparación."
                    )
                }
            }
        }

        Button(
            onClick = {
                if (
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    abrirCamara()
                } else {
                    permisoCamara.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = progreso == 100
        ) {
            Text("Tomar foto con la cámara")
        }

        OutlinedButton(
            onClick = {
                selectorGaleria.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = progreso == 100
        ) {
            Text("Elegir una foto de la galería")
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